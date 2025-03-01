package com.sunil.app.presentation.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.sunil.app.domain.repository.movies.MovieRepository
import com.sunil.app.domain.utils.DispatchersProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Worker responsible for synchronizing movie data.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */
@HiltWorker
class SyncMoviesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val movieRepository: MovieRepository,
    private val dispatchers: DispatchersProvider,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = withContext(dispatchers.io) {
        Timber.tag(TAG).d("SyncMoviesWorker: Starting movie synchronization.")
        return@withContext try {
            if (movieRepository.syncMovies()) {
                Timber.tag(TAG).d("SyncMoviesWorker: Movie synchronization successful.")
                Result.success()
            } else {
                handleSyncFailure()
            }
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "SyncMoviesWorker: An unexpected error occurred during synchronization.")
            handleSyncFailure()
        }
    }

    private fun handleSyncFailure(): Result {
        return if (runAttemptCount < MAX_SYNC_ATTEMPTS) {
            Timber.tag(TAG).w("SyncMoviesWorker: Movie synchronization failed. Retrying (attempt $runAttemptCount/$MAX_SYNC_ATTEMPTS).")
            Result.retry()
        } else {
            Timber.tag(TAG).e("SyncMoviesWorker: Movie synchronization failed after $MAX_SYNC_ATTEMPTS attempts.")
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SyncMoviesWorker"
        private const val MAX_SYNC_ATTEMPTS =3
        private const val INITIAL_BACKOFF_DELAY_SECONDS = 10L

        fun getSyncMoviesWorkRequest(): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<SyncMoviesWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    INITIAL_BACKOFF_DELAY_SECONDS,
                    TimeUnit.SECONDS
                )
                .build()
    }
}

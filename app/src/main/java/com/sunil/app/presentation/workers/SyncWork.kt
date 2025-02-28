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


/**
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */
@HiltWorker
class SyncWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val movieRepository: MovieRepository,
    private val dispatchers: DispatchersProvider,
) : CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result = withContext(dispatchers.io) {
        return@withContext if (movieRepository.sync()) {
            Timber.tag(TAG).d("SyncWork: doWork() called -> success")
            Result.success()
        } else {
            val lastAttempt = runAttemptCount >= SYNC_WORK_MAX_ATTEMPTS
            if (lastAttempt) {
                Timber.tag(TAG).d("SyncWork: doWork() called -> failure")
                Result.failure()
            } else {
                Timber.tag(TAG).d("SyncWork: doWork() called -> retry")
                Result.retry()
            }
        }
    }

    companion object {

        private const val TAG = "SyncWork"
        private const val SYNC_WORK_MAX_ATTEMPTS = 3

        fun getOneTimeWorkRequest() = OneTimeWorkRequestBuilder<SyncWork>()
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()
    }
}

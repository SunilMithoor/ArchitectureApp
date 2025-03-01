@file:Suppress("InjectDispatcher")

package com.sunil.app.domain.module


import com.sunil.app.data.utils.DispatchersProviderImpl
import com.sunil.app.domain.utils.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Singleton


/**
 * Module to provide Coroutine Dispatchers and DispatchersProvider.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-02-16
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    /**
     * Provides the IO CoroutineDispatcher.
     *
     * @return CoroutineDispatcher for IO operations.
     */
    @Provides
    @Singleton
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * Provides the Default CoroutineDispatcher.
     *
     * @returnCoroutineDispatcher for CPU-intensive operations.
     */
    @Provides
    @Singleton
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    /**
     * Provides the Main CoroutineDispatcher.
     *
     * @return MainCoroutineDispatcher for UI-related operations.
     */
    @Provides
    @Singleton
    @MainDispatcher
    fun providesMainDispatcher(): MainCoroutineDispatcher = Dispatchers.Main

    /**
     * Provides the DispatchersProvider.
     *
     * @param io The IO CoroutineDispatcher.
     * @param main The Main CoroutineDispatcher.
     * @param default The Default CoroutineDispatcher.
     * @param unconfined The Unconfined CoroutineDispatcher.
     * @return DispatchersProvider instance.
     */
    @Provides
    @Singleton
    fun providesDispatcherProvider(
        @IoDispatcher io: CoroutineDispatcher,
        @MainDispatcher main: MainCoroutineDispatcher,
        @DefaultDispatcher default: CoroutineDispatcher
    ): DispatchersProvider {
        return DispatchersProviderImpl(
            io = io,
            main = main,
            default = default,
            unconfined = Dispatchers.Unconfined
        )
    }
}

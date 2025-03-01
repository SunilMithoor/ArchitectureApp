package com.sunil.app.domain.module


import com.sunil.app.domain.manager.MoviesDataManager
import com.sunil.app.domain.manager.RestfulDataManager
import com.sunil.app.domain.repository.onboarding.OnBoardingRepository
import com.sunil.app.domain.usecase.movies.AddMovieToFavoritesUseCase
import com.sunil.app.domain.usecase.movies.CheckFavoriteStatusUseCase
import com.sunil.app.domain.usecase.movies.GetFavoriteMoviesUseCase
import com.sunil.app.domain.usecase.movies.GetMovieDetailsUseCase
import com.sunil.app.domain.usecase.movies.RemoveMovieFromFavoriteUseCase
import com.sunil.app.domain.usecase.movies.SearchMoviesUseCase
import com.sunil.app.domain.usecase.onboarding.GetDarkModeUseCase
import com.sunil.app.domain.usecase.onboarding.SetDarkModeUseCase
import com.sunil.app.domain.usecase.restful.GetAllDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Module for providing UseCase dependencies.
 *
 * This module is responsible for providing instances of UseCase classes
 * to be injected into other parts of the application.
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    /**
     * Provides an instance of [GetAllDataUseCase].
     *
     * @param restfulDataManager The [RestfulDataManager] instance to be used by the UseCase.
     * @return An instance of [GetAllDataUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideGetAllDataUseCase(restfulDataManager: RestfulDataManager): GetAllDataUseCase {
        return GetAllDataUseCase(restfulDataManager)
    }

    /**
     * Provides an instance of [SearchMoviesUseCase].
     *
     * @param moviesDataManager The [MoviesDataManager] instance to be used bythe UseCase.
     * @return An instance of [SearchMoviesUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideSearchMoviesUseCase(moviesDataManager: MoviesDataManager): SearchMoviesUseCase {
        return SearchMoviesUseCase(moviesDataManager)
    }

    /**
     * Provides an instance of [GetMovieDetailsUseCase].
     *
     * @param moviesDataManager The [MoviesDataManager] instance to be used by the UseCase.
     * @return An instance of [GetMovieDetailsUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideGetMovieDetailsUseCase(moviesDataManager: MoviesDataManager): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(moviesDataManager)
    }

    /**
     * Provides an instance of [GetFavoriteMoviesUseCase].
     *
     * @param moviesDataManager The [MoviesDataManager] instance to be used by the UseCase.
     * @return An instance of [GetFavoriteMoviesUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideGetFavoriteMoviesUseCase(moviesDataManager: MoviesDataManager): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCase(moviesDataManager)
    }

    /**
     * Provides an instance of [CheckFavoriteStatusUseCase].
     *
     * @param moviesDataManager The [MoviesDataManager] instance to be used by the UseCase.
     * @return An instance of [CheckFavoriteStatusUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideCheckFavoriteStatusUseCase(moviesDataManager: MoviesDataManager): CheckFavoriteStatusUseCase {
        return CheckFavoriteStatusUseCase(moviesDataManager)
    }

    /**
     * Provides an instance of [AddMovieToFavoritesUseCase].
     *
     * @param moviesDataManager The [MoviesDataManager] instance to be used by the UseCase.
     * @return An instance of [AddMovieToFavoritesUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideAddMovieToFavoriteUseCase(moviesDataManager: MoviesDataManager): AddMovieToFavoritesUseCase {
        return AddMovieToFavoritesUseCase(moviesDataManager)
    }

    /**
     * Provides an instance of [RemoveMovieFromFavoriteUseCase].
     *
     * @param moviesDataManager The [MoviesDataManager] instance to be used by the UseCase.
     * @return An instance of [RemoveMovieFromFavoriteUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideRemoveMovieFromFavoriteUseCase(moviesDataManager: MoviesDataManager): RemoveMovieFromFavoriteUseCase {
        return RemoveMovieFromFavoriteUseCase(moviesDataManager)
    }

    /**
     * Provides an instance of [GetDarkModeUseCase].
     *
     * @param repository The [OnBoardingRepository] instance to be used by the UseCase.
     * @return An instance of [GetDarkModeUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideGetDarkModeUseCase(repository: OnBoardingRepository): GetDarkModeUseCase {
        return GetDarkModeUseCase(repository)
    }

    /**
     * Provides an instance of [SetDarkModeUseCase].
     *
     * @param repository The [OnBoardingRepository] instance to be used by the UseCase.
     * @return An instance of [SetDarkModeUseCase].
     */
    @Provides
    @ViewModelScoped
    fun provideSetDarkModeUseCase(repository: OnBoardingRepository): SetDarkModeUseCase {
        return SetDarkModeUseCase(repository)
    }
}

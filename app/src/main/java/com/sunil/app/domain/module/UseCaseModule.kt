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

@InstallIn(ActivityComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideGetAllDataCase(restfulDataManager: RestfulDataManager): GetAllDataUseCase =
        GetAllDataUseCase(
            restfulDataManager
        )

    @Provides
    fun provideSearchMoviesUseCase(moviesDataManager: MoviesDataManager): SearchMoviesUseCase =
        SearchMoviesUseCase(
            moviesDataManager
        )

    @Provides
    fun provideGetMovieDetailsUseCase(moviesDataManager: MoviesDataManager): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(
            moviesDataManager
        )

    @Provides
    fun provideGetFavoriteMoviesUseCase(moviesDataManager: MoviesDataManager): GetFavoriteMoviesUseCase =
        GetFavoriteMoviesUseCase(
            moviesDataManager
        )

    @Provides
    fun provideCheckFavoriteStatusUseCase(moviesDataManager: MoviesDataManager): CheckFavoriteStatusUseCase =
        CheckFavoriteStatusUseCase(
            moviesDataManager
        )

    @Provides
    fun provideAddMovieToFavoriteUseCase(moviesDataManager: MoviesDataManager): AddMovieToFavoritesUseCase =
        AddMovieToFavoritesUseCase(
            moviesDataManager
        )

    @Provides
    fun provideRemoveMovieFromFavoriteUseCase(moviesDataManager: MoviesDataManager): RemoveMovieFromFavoriteUseCase =
        RemoveMovieFromFavoriteUseCase(
            moviesDataManager
        )


    /**
     * Returns a [GetDarkModeUseCase] instance
     * @param repository [OnBoardingRepository] impl
     * @since 1.0.0
     */
    @Provides
    fun provideGetDarkModeUseCase(repository: OnBoardingRepository): GetDarkModeUseCase =
        GetDarkModeUseCase(
            repository
        )

    /**
     * Returns a [SetDarkModeUseCase] instance
     * @param repository [OnBoardingRepository] impl
     * @since 1.0.0
     */
    @Provides
    fun provideSetDarkModeUseCase(repository: OnBoardingRepository): SetDarkModeUseCase =
        SetDarkModeUseCase(
            repository
        )
}

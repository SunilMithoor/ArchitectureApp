package com.sunil.app.presentation.viewmodel.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sunil.app.base.BaseViewModel
import com.sunil.app.domain.entity.movies.MovieEntity
import com.sunil.app.domain.model.Result
import com.sunil.app.domain.model.getOrNull
import com.sunil.app.domain.model.onError
import com.sunil.app.domain.model.onSuccess
import com.sunil.app.domain.usecase.movies.AddMovieToFavoritesUseCase
import com.sunil.app.domain.usecase.movies.CheckFavoriteStatusUseCase
import com.sunil.app.domain.usecase.movies.GetFavoriteMoviesUseCase
import com.sunil.app.domain.usecase.movies.GetMovieDetailsUseCase
import com.sunil.app.domain.usecase.movies.GetMoviesWithSeparatorsUseCase
import com.sunil.app.domain.usecase.movies.RemoveMovieFromFavoriteUseCase
import com.sunil.app.domain.usecase.movies.SearchMoviesUseCase
import com.sunil.app.domain.utils.NetworkMonitor
import com.sunil.app.presentation.entity.movies.MovieListItem
import com.sunil.app.presentation.mapper.movies.toMovieListItem
import com.sunil.app.presentation.ui.screens.movies.favourites.FavoriteUiState
import com.sunil.app.presentation.ui.screens.movies.favourites.FavoritesNavigationState
import com.sunil.app.presentation.ui.screens.movies.feed.FeedNavigationState
import com.sunil.app.presentation.ui.screens.movies.feed.FeedUiState
import com.sunil.app.presentation.ui.screens.movies.moviedetails.MovieDetailsBundle
import com.sunil.app.presentation.ui.screens.movies.moviedetails.MovieDetailsState
import com.sunil.app.presentation.ui.screens.movies.search.SearchNavigationState
import com.sunil.app.presentation.ui.screens.movies.search.SearchUiState
import com.sunil.app.presentation.ui.util.singleSharedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


/**
 * ViewModel for managing movie-related data and UI state.
 *
 * This ViewModel handles fetching, searching, and managing movies, including favorites,
 * feed, and search functionalities. It also manages the UI state and navigation for
 * these features.
 */
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
    private val checkFavoriteStatusUseCase: CheckFavoriteStatusUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getMoviesWithSeparatorsUseCase: GetMoviesWithSeparatorsUseCase,
    private val movieDetailsBundle: MovieDetailsBundle,
    private val savedStateHandle: SavedStateHandle,
    private val networkMonitor: NetworkMonitor,
) : BaseViewModel() {

    companion object {
        private const val TAG = "MoviesViewModel"
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val DEFAULT_PAGE_SIZE = 30
        private const val FEED_PAGE_SIZE = 90
        private const val SEARCH_DEBOUNCE_TIME_MS = 500L

    }

    init {
        observeNetworkStatus()
        loadMovieDetails()
    }


    //Favourites
    //start
    /**
     * StateFlow to hold the UI state for the favorite movies screen.
     */
    private val _uiFavouriteMovieState: MutableStateFlow<FavoriteUiState> =
        MutableStateFlow(FavoriteUiState())
    val uiFavouriteMovieState = _uiFavouriteMovieState.asStateFlow()

    /**
     * SharedFlow for navigation events from the favorite movies screen.
     */
    private val _navigationFavouriteMovieState: MutableSharedFlow<FavoritesNavigationState> =
        singleSharedFlow()
    val navigationFavouriteMovieState = _navigationFavouriteMovieState.asSharedFlow()

    /**
     * Flow of favorite movies, represented as PagingData.
     */
    val favouriteMovies: Flow<PagingData<MovieListItem>> =
        getFavoriteMoviesUseCase.invoke(DEFAULT_PAGE_SIZE)
            .map { pagingData ->
                pagingData.map { it.toMovieListItem() }
            }
            .cachedIn(coroutineScope)


    /**
     * Handles a click on a favorite movie item.
     *
     * @param movieId The ID of the clicked movie.
     */
    fun onFavouriteMovieClicked(movieId: Int) {
        _navigationFavouriteMovieState.tryEmit(FavoritesNavigationState.MovieDetails(movieId))
    }

    /**
     * Updates the UI state based on the load state of the favorite movies.
     *
     * @param loadState The combined load state of the PagingData.
     * @param itemCount The current number of items in the PagingData.
     */
    fun onFavouriteMovieLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) {
        val showLoading = loadState.refresh is LoadState.Loading
        val showNoData = loadState.append.endOfPaginationReached && itemCount < 1

        _uiFavouriteMovieState.update {
            it.copy(
                isLoading = showLoading,
                noDataAvailable = showNoData
            )
        }
    }
    //end

    //Search
    //start

    /**
     * StateFlow to hold the UI state for the search movies screen.
     */
    private val _uiSearchMovieState: MutableStateFlow<SearchUiState> = MutableStateFlow(
        SearchUiState()
    )
    val uiSearchMovieState = _uiSearchMovieState.asStateFlow()

    /**
     * SharedFlow for navigation events from the search movies screen.
     */
    private val _navigationSearchMovieState: MutableSharedFlow<SearchNavigationState> =
        singleSharedFlow()
    val navigationSearchMovieState = _navigationSearchMovieState.asSharedFlow()

    /**
     * Flow of search results, represented as PagingData.
     */
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchMovies: Flow<PagingData<MovieListItem>> =
        savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")
            .debounce(if (_uiSearchMovieState.value.showDefaultState) 0 else SEARCH_DEBOUNCE_TIME_MS)
            .onEach { query ->
                _uiSearchMovieState.update {
                    if (query.isNotEmpty()) {
                        it.copy(showDefaultState = false, showLoading = true)
                    } else {
                        SearchUiState()
                    }
                }
            }
            .filter { it.isNotEmpty() }
            .flatMapLatest { query ->
                searchMoviesUseCase(query, DEFAULT_PAGE_SIZE).map { pagingData ->
                    pagingData.map { it.toMovieListItem() }
                }
            }
            .cachedIn(viewModelScope)


    /**
     * Handles a search query.
     *
     * @param query The search query.
     */
    fun onSearchMovie(query: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = query
    }

    /**
     * Handles a click on a search result movie item.
     *
     * @param movieId The ID of the clicked movie.
     */
    fun onSearchMovieClicked(movieId: Int) {
        _navigationSearchMovieState.tryEmit(SearchNavigationState.MovieDetails(movieId))
    }


    fun onSearchMovieLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) {
        val showLoading = loadState.refresh is LoadState.Loading
        val showNoData = loadState.append.endOfPaginationReached && itemCount < 1

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiSearchMovieState.update { currentState ->
            currentState.copy(
                showLoading = showLoading,
                showNoMoviesFound = showNoData,
                errorMessage = error
            )
        }
    }
    //end


    //Feed
    //start

    /**
     * Flow of movie items to be displayed in the feed.
     */
    val movieFeed: Flow<PagingData<MovieListItem>> = getMoviesWithSeparatorsUseCase.invoke(
        pageSize = FEED_PAGE_SIZE
    ).cachedIn(coroutineScope)

    /**
     * StateFlow representing the current UI state of the feed screen.
     */
    private val _uiFeedMovieState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState())
    val uiFeedMovieState: StateFlow<FeedUiState> = _uiFeedMovieState.asStateFlow()

    /**
     * SharedFlow for navigation events from the feed screen.
     */
    private val _navigationFeedMovieState: MutableSharedFlow<FeedNavigationState> =
        singleSharedFlow()
    val navigationFeedMovieState: SharedFlow<FeedNavigationState> =
        _navigationFeedMovieState.asSharedFlow()

    /**
     * SharedFlow used to trigger a refresh of the movie list.
     */
    private val _refreshTrigger: MutableSharedFlow<Unit> = singleSharedFlow()
    val refreshTrigger: SharedFlow<Unit> = _refreshTrigger.asSharedFlow()

    /**
     * Observes changes in network connectivity and triggers a refresh if necessary.
     */
    private fun observeNetworkStatus() {
        networkMonitor.networkState
            .onEach { networkState ->
                if (networkState.shouldRefresh && networkState.isOnline) {
                    onRefresh()
                }
            }
            .launchIn(coroutineScope)
    }

    /**
     * Handles a click on a movie item in the feed.
     *
     * @param movieId The ID of the clicked movie.
     */
    fun onMovieClicked(movieId: Int) {
        _navigationFeedMovieState.tryEmit(FeedNavigationState.MovieDetails(movieId))
    }

    /**
     * Updates the UI state based on the load state of the movie feed.
     *
     * @param loadState The combined load state of the PagingData.
     */
    fun onLoadStateChanged(loadState: CombinedLoadStates) {
        val showLoading = loadState.refresh is LoadState.Loading

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiFeedMovieState.update { it.copy(showLoading = showLoading, errorMessage = error) }
    }

    /**
     * Triggers a refresh of the movie list.
     */
    fun onRefresh() {
        coroutineScope.launch {
            _refreshTrigger.emit(Unit)
        }
    }


    /**
     * StateFlow representing the current UI state of the movie details screen.
     */
    private val _uiMovieDetailsState: MutableStateFlow<MovieDetailsState> = MutableStateFlow(
        MovieDetailsState()
    )
    val uiMovieDetailsState = _uiMovieDetailsState.asStateFlow()

    /**
     *The ID of the movie to display.
     */
    private val movieId: Int = movieDetailsBundle.movieId

    /**
     * Loads the movie details and updates the UI state.
     *
     * This function fetches the movie details and the favorite status concurrently.
     * It then updates the UI state with the retrieved data.
     */
    private fun loadMovieDetails() {
        coroutineScope.launch {
            // Use coroutineScope to run async and getMovieById concurrently
            coroutineScope {
                // Fetch the favorite status concurrently
                val isFavoriteDeferred = async { checkFavoriteStatus(movieId).getOrNull() }
                // Fetch the movie details
                val movieDetailsResult = getMovieById(movieId)

                // Update the UI state with the movie details and favorite status
                movieDetailsResult.onSuccess { movie ->
                    _uiMovieDetailsState.update {
                        it.copy(
                            title = movie.title,
                            description = movie.description,
                            imageUrl = movie.backgroundImageUrl,
                            isFavorite = isFavoriteDeferred.await() == true // Await the favorite status
                        )
                    }
                }.onError {
                    // Handle failure, e.g., show an error message
                    Timber.tag(TAG).e("Error loading movie details: ${it.message}")
                    // Optionally update the state to reflect the error
                }
            }
        }
    }

    /**
     * Handles a click on the favorite movie button.
     *
     * This function checks the current favorite status of the movie, toggles it,
     * and updates the UI state.
     */
    fun onFavoriteMovieClicked() {
        coroutineScope.launch {
            // Use withContext(Dispatchers.IO) for database operations
            val result = withContext(Dispatchers.IO) {
                checkFavoriteStatus(movieId)
            }
            result.onSuccess { isFavorite ->
                // Toggle the favorite status
                if (isFavorite) {
                    removeMovieFromFavoriteUseCase.invoke(movieId)
                } else {
                    addMovieToFavoritesUseCase.invoke(movieId)
                    _uiMovieDetailsState.update { it.copy(isFavorite = !isFavorite) }
                }

//                favoriteResult.onSuccess {
//                    // Update the UI state with the new favorite status
//                    _uiMovieDetailsState.update { it.copy(isFavorite = !isFavorite) }
//                }.onFailure {
//                    // Handle failure, e.g., show an error message
//                    Timber.tag(TAG).e("Error updating favorite status: ${e.message}")
//                }
            }.onError {
                // Handle failure, e.g., show an error message
                Timber.tag(TAG).e("Error checking favorite status: ${it.message}")
            }
        }
    }

    /**
     * Retrieves the details of a movie by its ID.
     *
     * @param movieId The ID of the movie.
     * @return A Result containing the MovieEntity or an error.
     */
    private suspend fun getMovieById(movieId: Int): Result<MovieEntity> =
        getMovieDetailsUseCase.invoke(movieId)

    /**
     * Checks if a movie is marked as a favorite.
     *
     * @param movieId The ID of the movie.
     * @return A Result containing true if the movie is a favorite, false otherwise, or an error.
     */
    private suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> =
        checkFavoriteStatusUseCase.invoke(movieId)

}

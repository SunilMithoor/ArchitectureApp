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
import com.sunil.app.domain.usecase.movies.AddMovieToFavoritesUseCase
import com.sunil.app.domain.usecase.movies.CheckFavoriteStatusUseCase
import com.sunil.app.domain.usecase.movies.GetFavoriteMoviesUseCase
import com.sunil.app.domain.usecase.movies.GetMovieDetailsUseCase
import com.sunil.app.domain.usecase.movies.GetMoviesWithSeparatorsUseCase
import com.sunil.app.domain.usecase.movies.RemoveMovieFromFavoriteUseCase
import com.sunil.app.domain.usecase.movies.SearchMoviesUseCase
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
import com.sunil.app.presentation.util.CodeSnippet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.sunil.app.domain.model.Result
import com.sunil.app.domain.model.asSuccessOrNull
import com.sunil.app.domain.model.onSuccess
import com.sunil.app.presentation.ui.util.orFalse


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
    codeSnippet: CodeSnippet,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel(codeSnippet) {

    companion object {
        private const val TAG = "MoviesViewModel"
        private const val KEY_SEARCH_QUERY = "search_query"
    }

    //favourites
    val favouriteMovies: Flow<PagingData<MovieListItem>> =
        getFavoriteMoviesUseCase.invoke(30).map { pagingData ->
            pagingData.map { movieEntity ->
                movieEntity.toMovieListItem()
            }
        }.cachedIn(coroutineScope)

    private val _uiFavouriteMovieState: MutableStateFlow<FavoriteUiState> =
        MutableStateFlow(FavoriteUiState())
    val uiFavouriteMovieState = _uiFavouriteMovieState.asStateFlow()

    private val _navigationFavouriteMovieState: MutableSharedFlow<FavoritesNavigationState> =
        singleSharedFlow()
    val navigationFavouriteMovieState = _navigationFavouriteMovieState.asSharedFlow()

    fun onFavouriteMovieClicked(movieId: Int) =
        _navigationFavouriteMovieState.tryEmit(FavoritesNavigationState.MovieDetails(movieId))

    fun onFavouriteMovieLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) {
        val showLoading = loadState.refresh is LoadState.Loading
        val showNoData = loadState.append.endOfPaginationReached && itemCount < 1

        _uiFavouriteMovieState.update {
            it.copy(
                isLoading = showLoading, noDataAvailable = showNoData
            )
        }
    }


    //search
    private val _uiSearchMovieState: MutableStateFlow<SearchUiState> = MutableStateFlow(
        SearchUiState()
    )
    val uiSearchMovieState = _uiSearchMovieState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    var searchMovies: Flow<PagingData<MovieListItem>> =
        savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")
            .debounce(if (_uiSearchMovieState.value.showDefaultState) 0 else 500).onEach { query ->
                _uiSearchMovieState.value = if (query.isNotEmpty()) SearchUiState(
                    showDefaultState = false, showLoading = true
                ) else SearchUiState()
            }.filter { it.isNotEmpty() }.flatMapLatest { query ->
                searchMoviesUseCase.invoke(query, 30).map { pagingData ->
                    pagingData.map { movieEntity -> movieEntity.toMovieListItem() }
                }
            }.cachedIn(viewModelScope)

    private val _navigationSearchMovieState: MutableSharedFlow<SearchNavigationState> =
        singleSharedFlow()
    val navigationSearchMovieState = _navigationSearchMovieState.asSharedFlow()

    fun onSearchMovie(query: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = query
    }

    fun onSearchMovieClicked(movieId: Int) =
        _navigationSearchMovieState.tryEmit(SearchNavigationState.MovieDetails(movieId))

    fun onSearchMovieLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) {
        val showLoading = loadState.refresh is LoadState.Loading
        val showNoData = loadState.append.endOfPaginationReached && itemCount < 1

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiSearchMovieState.update {
            it.copy(
                showLoading = showLoading, showNoMoviesFound = showNoData, errorMessage = error
            )
        }
    }


    //feed
    val feedMovies: Flow<PagingData<MovieListItem>> = getMoviesWithSeparatorsUseCase.movies(
        pageSize = 90
    ).cachedIn(coroutineScope)

    private val _uiFeedMovieState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState())
    val uiFeedMovieState = _uiFeedMovieState.asStateFlow()

    private val _navigationFeedMovieState: MutableSharedFlow<FeedNavigationState> =
        singleSharedFlow()
    val navigationFeedMovieState = _navigationFeedMovieState.asSharedFlow()

    private val _refreshFeedMovieListState: MutableSharedFlow<Unit> = singleSharedFlow()
    val refreshFeedMovieListState = _refreshFeedMovieListState.asSharedFlow()

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
//        networkMonitor.networkState.onEach { if (it.shouldRefresh) onRefresh() }
//            .launchIn(viewModelScope)
    }

    fun onFeedMovieClicked(movieId: Int) =
        _navigationFeedMovieState.tryEmit(FeedNavigationState.MovieDetails(movieId))

    fun onFeedMovieLoadStateUpdate(loadState: CombinedLoadStates) {
        val showLoading = loadState.refresh is LoadState.Loading

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiFeedMovieState.update { it.copy(showLoading = showLoading, errorMessage = error) }
    }

    fun onFeedMovieRefresh() = coroutineScope.launch {
        _refreshFeedMovieListState.emit(Unit)
    }

    //movie details

    private val _uiMovieDetailsState: MutableStateFlow<MovieDetailsState> = MutableStateFlow(
        MovieDetailsState()
    )
    val uiState = _uiMovieDetailsState.asStateFlow()

    private val movieId: Int = movieDetailsBundle.movieId

    init {
        onInitialState()
    }

    private fun onInitialState() = coroutineScope.launch {
        val isFavorite = async { checkFavoriteStatus(movieId).asSuccessOrNull().orFalse() }
        getMovieById(movieId).onSuccess {
            _uiMovieDetailsState.value = MovieDetailsState(
                title = it.title,
                description = it.description,
                imageUrl = it.backgroundUrl,
                isFavorite = isFavorite.await()
            )
        }
    }

//    fun onFavoriteMovieClicked() = launch {
//        checkFavoriteStatus(movieId).onSuccess { isFavorite ->
//            if (isFavorite) removeMovieFromFavorite(movieId)
//            else
//                addMovieToFavoritesUseCase.invoke(movieId)
//            _uiMovieDetailsState.update { it.copy(isFavorite = !isFavorite) }
//        }
//    }


    fun onFavoriteMovieClicked() {
        coroutineScope.launch {
            checkFavoriteStatus(movieId).onSuccess { isFavorite ->
                if (isFavorite) removeMovieFromFavoriteUseCase.invoke(movieId)
                else addMovieToFavoritesUseCase.invoke(movieId)
                _uiMovieDetailsState.update { it.copy(isFavorite = !isFavorite) }
            }
        }
    }


    private suspend fun getMovieById(movieId: Int): Result<MovieEntity> =
        getMovieDetailsUseCase.invoke(movieId)


    private suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = checkFavoriteStatusUseCase.invoke(movieId)

}

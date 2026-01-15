package eone.grim.moviebrowser.presentation.movies.list

import eone.grim.moviebrowser.data.images.ImageConfigStore
import eone.grim.moviebrowser.domain.entity.Movie
import eone.grim.moviebrowser.domain.usecase.GetPopularMovies
import eone.grim.moviebrowser.domain.usecase.ObserveFavoriteIds
import eone.grim.moviebrowser.domain.usecase.ToggleFavorite
import eone.grim.moviebrowser.presentation.model.toUI
import eone.grim.moviebrowser.presentation.mvi.MviViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MovieListViewModel(
    private val getPopular: GetPopularMovies,
    private val observeFavoriteIds: ObserveFavoriteIds,
    private val toggleFavorite: ToggleFavorite,
    private val images: ImageConfigStore,
    private val scope: CoroutineScope
) : MviViewModel<MovieListContract.Intent, MovieListContract.State, MovieListContract.Effect>(
    MovieListContract.State()
) {
    private var job: Job? = null
    private var favorites: Set<Long> = emptySet()

    init {
        scope.launch {
            observeFavoriteIds().collect { ids ->
                favorites = ids
                val current = _state.value
                _state.value = current.copy(items = current.items.map { it.copy(isFavorite = ids.contains(it.id)) })
            }
        }
    }

    override fun onIntent(intent: MovieListContract.Intent) {
        when (intent) {
            MovieListContract.Intent.OnAppear -> load(page = 1)
            MovieListContract.Intent.OnRetry -> load(page = _state.value.page)
            is MovieListContract.Intent.OnMovieClick -> scope.launch {
                _effects.send(MovieListContract.Effect.NavigateToDetails(intent.id))
            }
            is MovieListContract.Intent.OnToggleFavorite -> {
                val ui = _state.value.items.firstOrNull { it.id == intent.id } ?: return
                val domain = Movie(
                    id = ui.id,
                    title = ui.title,
                    overview = ui.overview,
                    posterPath = ui.posterPath,
                    releaseDate = ui.releaseDate
                )
                scope.launch {
                    toggleFavorite(domain)
                    _state.update { st ->
                        st.copy(items = st.items.map {
                            if (it.id == ui.id) it.copy(isFavorite = !it.isFavorite) else it
                        })
                    }
                }
            }

        }
    }

    private fun load(page: Int) {
        job?.cancel()
        job = scope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null, page = page)
            try {
                val movies = getPopular(page)
                val ui = movies.map { it.toUI(images, favorites.contains(it.id)) }
                _state.value = _state.value.copy(isLoading = false, items = ui)
            } catch (t: Throwable) {
                _state.value = _state.value.copy(isLoading = false, error = t.message ?: "Unknown error")
            }
        }
    }
}

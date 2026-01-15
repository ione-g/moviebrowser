package eone.grim.moviebrowser.presentation.movies.favorites

import eone.grim.moviebrowser.data.images.ImageConfigStore
import eone.grim.moviebrowser.domain.usecase.ObserveFavorites
import eone.grim.moviebrowser.domain.usecase.RemoveFavorite
import eone.grim.moviebrowser.presentation.model.toUI
import eone.grim.moviebrowser.presentation.mvi.MviViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val observeFavorites: ObserveFavorites,
    private val removeFavorite: RemoveFavorite,
    private val images: ImageConfigStore,
    private val scope: CoroutineScope
) : MviViewModel<FavoritesContract.Intent, FavoritesContract.State, FavoritesContract.Effect>(
    FavoritesContract.State()
) {
    private var observing = false

    override fun onIntent(intent: FavoritesContract.Intent) {
        when (intent) {
            FavoritesContract.Intent.OnAppear -> startObserveOnce()
            is FavoritesContract.Intent.OnMovieClick -> scope.launch {
                _effects.send(FavoritesContract.Effect.NavigateToDetails(intent.id))
            }
            is FavoritesContract.Intent.OnRemove -> scope.launch {
                removeFavorite(intent.id)
            }
        }
    }

    private fun startObserveOnce() {
        if (observing) return
        observing = true
        scope.launch {
            observeFavorites().collectLatest { list ->
                val ui = list.map { it.toUI(images, isFavorite = true) }
                _state.value = FavoritesContract.State(items = ui, isEmpty = ui.isEmpty())
            }
        }
    }
}
package eone.grim.moviebrowser.presentation.movies.favorites

import eone.grim.moviebrowser.presentation.model.MovieUI
import eone.grim.moviebrowser.presentation.mvi.MviEffect
import eone.grim.moviebrowser.presentation.mvi.MviIntent
import eone.grim.moviebrowser.presentation.mvi.MviState

object FavoritesContract {

    sealed interface Intent : MviIntent {
        data object OnAppear : Intent
        data class OnMovieClick(val id: Long) : Intent
        data class OnRemove(val id: Long) : Intent
    }

    data class State(
        val items: List<MovieUI> = emptyList(),
        val isEmpty: Boolean = true
    ) : MviState

    sealed interface Effect : MviEffect {
        data class NavigateToDetails(val id: Long) : Effect
    }
}
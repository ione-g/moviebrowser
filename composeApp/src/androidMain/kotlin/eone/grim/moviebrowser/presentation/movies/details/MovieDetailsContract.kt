package eone.grim.moviebrowser.presentation.movies.details

import eone.grim.moviebrowser.presentation.model.MovieUI
import eone.grim.moviebrowser.presentation.mvi.MviEffect
import eone.grim.moviebrowser.presentation.mvi.MviIntent
import eone.grim.moviebrowser.presentation.mvi.MviState

object MovieDetailsContract {

    sealed interface Intent : MviIntent {
        data class Load(val id: Long) : Intent
        data object ToggleFavorite : Intent
    }

    data class State(
        val isLoading: Boolean = false,
        val movie: MovieUI? = null,
        val error: String? = null
    ) : MviState

    sealed interface Effect : MviEffect {
        data class ShowMessage(val text: String) : Effect
    }
}
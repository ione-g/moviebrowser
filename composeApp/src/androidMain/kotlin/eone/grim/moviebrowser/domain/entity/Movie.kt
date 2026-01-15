package eone.grim.moviebrowser.domain.entity

import androidx.compose.foundation.interaction.PressInteraction

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?
)

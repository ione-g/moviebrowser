package eone.grim.moviebrowser.presentation.model


data class MovieUI(
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val isFavorite: Boolean
)
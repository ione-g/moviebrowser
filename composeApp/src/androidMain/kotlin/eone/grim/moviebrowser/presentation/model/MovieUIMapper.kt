package eone.grim.moviebrowser.presentation.model

import eone.grim.moviebrowser.data.images.ImageConfigStore
import eone.grim.moviebrowser.domain.entity.Movie

suspend fun Movie.toUI(
    images: ImageConfigStore,
    isFavorite: Boolean
): MovieUI = MovieUI (
    id = id,
    title = title,
    overview = overview,
    posterUrl = images.posterUrl(posterPath),
    posterPath = posterPath,
    releaseDate = releaseDate?.take(4),
    isFavorite = isFavorite
)
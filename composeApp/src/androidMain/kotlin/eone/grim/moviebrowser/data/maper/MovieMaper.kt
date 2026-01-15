package eone.grim.moviebrowser.data.maper

import eone.grim.moviebrowser.data.remote.dto.MovieDto
import eone.grim.moviebrowser.domain.entity.Movie

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate
)
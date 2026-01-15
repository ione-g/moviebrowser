package eone.grim.moviebrowser.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int
)
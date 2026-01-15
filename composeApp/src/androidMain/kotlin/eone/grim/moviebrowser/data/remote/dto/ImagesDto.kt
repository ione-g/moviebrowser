package eone.grim.moviebrowser.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesDto(
    @SerialName("secure_base_url") val secureBaseUrl: String,
    @SerialName("poster_sizes") val posterSizes: List<String>
)
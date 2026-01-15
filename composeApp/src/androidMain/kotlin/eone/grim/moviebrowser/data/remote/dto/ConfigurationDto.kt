package eone.grim.moviebrowser.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationDto(
    val images: ImagesDto
)
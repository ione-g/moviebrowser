package eone.grim.moviebrowser.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import eone.grim.moviebrowser.data.remote.dto.ConfigurationDto
import eone.grim.moviebrowser.data.remote.dto.MovieDto
import eone.grim.moviebrowser.data.remote.dto.PopularMoviesResponseDto

class TmdbApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun getPopular(page: Int, language: String? = null): PopularMoviesResponseDto =
        client.get("${baseUrl}movie/popular") {
            parameter("page", page)
            language?.let { parameter("language", it) }
        }.body()

    suspend fun getDetails(id: Long, language: String? = null): MovieDto =
        client.get("${baseUrl}movie/$id") {
            language?.let { parameter("language", it) }
        }.body()

    suspend fun getConfiguration(): ConfigurationDto =
        client.get("${baseUrl}configuration").body()
}
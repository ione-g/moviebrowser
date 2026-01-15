package eone.grim.moviebrowser.data.repository

import eone.grim.moviebrowser.data.maper.toDomain
import eone.grim.moviebrowser.data.remote.TmdbApi
import eone.grim.moviebrowser.domain.entity.Movie
import eone.grim.moviebrowser.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: TmdbApi
) : MovieRepository {

    override suspend fun getPopular(page: Int, language: String?): List<Movie> =
        api.getPopular(page, language).results.map { it.toDomain() }

    override suspend fun getDetails(id: Long, language: String?): Movie =
        api.getDetails(id, language).toDomain()
}
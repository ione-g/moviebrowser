package eone.grim.moviebrowser.domain.repository

import eone.grim.moviebrowser.domain.entity.Movie

interface MovieRepository {
    suspend fun getPopular(page: Int, language: String? = null): List<Movie>
    suspend fun getDetails(id: Long, language: String? = null): Movie

}
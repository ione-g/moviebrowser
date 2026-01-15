package eone.grim.moviebrowser.domain.repository

import eone.grim.moviebrowser.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteMoviesRepository {
    fun observeFavorites(): Flow<List<Movie>>
    fun observeFavoriteIds(): Flow<Set<Long>>

    suspend fun isFavorite(id: Long): Boolean
    suspend fun add(movie: Movie)
    suspend fun remove(id: Long)
    suspend fun toggle(movie: Movie)
}
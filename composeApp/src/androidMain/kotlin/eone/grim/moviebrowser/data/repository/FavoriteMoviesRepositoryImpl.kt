package eone.grim.moviebrowser.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import eone.grim.moviebrowser.data.db.AppDatabase
import eone.grim.moviebrowser.domain.entity.Movie
import eone.grim.moviebrowser.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteMoviesRepositoryImpl(
    private val db: AppDatabase
) : FavoriteMoviesRepository {

    private val q = db.favoritesQueries

    override fun observeFavorites(): Flow<List<Movie>> {
        return q.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { rows ->
                rows.map { row ->
                    Movie(
                        id = row.id,
                        title = row.title,
                        overview = row.overview,
                        posterPath = row.posterPath,
                        releaseDate = row.releaseDate
                    )
                }
            }
    }

    override fun observeFavoriteIds(): Flow<Set<Long>> {
        return q.selectIds()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { rows ->
                 rows.toSet()
            }
    }

    override suspend fun isFavorite(id: Long): Boolean {
        return q.selectById(id).executeAsOneOrNull() != null
    }

    override suspend fun add(movie: Movie) {
        q.insertMovie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            addedAt = System.currentTimeMillis()
        )
    }

    override suspend fun remove(id: Long) {
        q.deleteById(id)
    }

    override suspend fun toggle(movie: Movie) {
        if (isFavorite(movie.id)) {
            remove(movie.id)
        } else {
            add(movie)
        }
    }
}

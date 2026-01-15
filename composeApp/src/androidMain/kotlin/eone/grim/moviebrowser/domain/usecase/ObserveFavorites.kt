package eone.grim.moviebrowser.domain.usecase

import eone.grim.moviebrowser.domain.entity.Movie
import eone.grim.moviebrowser.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavorites(private val repo: FavoriteMoviesRepository) {
    operator fun invoke(): Flow<List<Movie>> = repo.observeFavorites()
}
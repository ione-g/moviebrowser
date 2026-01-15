package eone.grim.moviebrowser.domain.usecase

import eone.grim.moviebrowser.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavoriteIds(private val repo: FavoriteMoviesRepository) {
    operator fun invoke(): Flow<Set<Long>> = repo.observeFavoriteIds()
}
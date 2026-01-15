package eone.grim.moviebrowser.domain.usecase

import eone.grim.moviebrowser.domain.repository.FavoriteMoviesRepository

class RemoveFavorite(private val repo: FavoriteMoviesRepository) {
    suspend operator fun invoke(id: Long) = repo.remove(id)
}
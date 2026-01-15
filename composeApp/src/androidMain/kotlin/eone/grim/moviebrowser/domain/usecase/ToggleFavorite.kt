package eone.grim.moviebrowser.domain.usecase

import eone.grim.moviebrowser.domain.entity.Movie
import eone.grim.moviebrowser.domain.repository.FavoriteMoviesRepository

class ToggleFavorite(private val repo: FavoriteMoviesRepository) {
    suspend operator fun invoke(movie: Movie) = repo.toggle(movie)
}
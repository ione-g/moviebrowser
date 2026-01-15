package eone.grim.moviebrowser.domain.usecase

import eone.grim.moviebrowser.domain.entity.Movie
import eone.grim.moviebrowser.domain.repository.MovieRepository

class GetPopularMovies(private val repo: MovieRepository) {
    suspend operator fun invoke(page: Int): List<Movie> = repo.getPopular(page)
}
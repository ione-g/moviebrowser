package eone.grim.moviebrowser.domain.usecase

import eone.grim.moviebrowser.domain.entity.Movie
import eone.grim.moviebrowser.domain.repository.MovieRepository

class GetMovieDetails(private val repo: MovieRepository) {
    suspend operator fun invoke(id: Long): Movie = repo.getDetails(id)
}
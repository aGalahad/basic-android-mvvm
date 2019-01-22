package appsynth.galahad.mvvm.usecase

import appsynth.galahad.mvvm.api.repo.MovieRepo
import appsynth.galahad.mvvm.model.MovieDetail
import io.reactivex.Observable

interface RelateMovieUseCase {
    fun execute(movieId: String): Observable<List<MovieDetail>>
}

class RelateMovieUseCaseImpl(private val movieRepo: MovieRepo) : RelateMovieUseCase {

    override fun execute(movieId: String) = movieRepo.getRelateMovie(id = movieId)

}
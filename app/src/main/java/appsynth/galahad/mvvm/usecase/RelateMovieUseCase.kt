package appsynth.galahad.mvvm.usecase

import appsynth.galahad.mvvm.api.repo.MovieRepo
import appsynth.galahad.mvvm.model.MovieDetail
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

interface RelateMovieUseCase {
    fun execute(movieId: String): Observable<List<MovieDetail>>
}

class RelateMovieUseCaseImpl(private val movieRepo: MovieRepo) : RelateMovieUseCase {

    override fun execute(movieId: String) =
        Observable.timer(2000, TimeUnit.MILLISECONDS)
            .flatMap {
                movieRepo.getRelateMovie(id = movieId)
            }
}
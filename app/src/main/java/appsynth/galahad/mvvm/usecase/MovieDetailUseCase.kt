package appsynth.galahad.mvvm.usecase

import appsynth.galahad.mvvm.api.repo.MovieRepo
import appsynth.galahad.mvvm.model.MovieDetail
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

interface MovieDetailUseCase {
    fun execute(movieId: String): Observable<MovieDetail>
}

class MovieDetailUseCaseImpl(private val movieRepo: MovieRepo) : MovieDetailUseCase {

    override fun execute(movieId: String) =
        Observable.timer(1000, TimeUnit.MILLISECONDS)
            .flatMap {
                movieRepo.getMovieDetail(id = movieId)
            }

}
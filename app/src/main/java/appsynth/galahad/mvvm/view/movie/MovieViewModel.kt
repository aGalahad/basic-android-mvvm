package appsynth.galahad.mvvm.view.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import appsynth.galahad.mvvm.extenstions.addTo
import appsynth.galahad.mvvm.model.MovieDetail
import appsynth.galahad.mvvm.usecase.MovieDetailUseCase
import appsynth.galahad.mvvm.usecase.RelateMovieUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel(private val movieDetailUseCase: MovieDetailUseCase) : ViewModel() {

    companion object {
        private const val TAG = "MovieViewModel"
    }

    private val compositeDisposable = CompositeDisposable()
    private val movieDetail = MutableLiveData<MovieDetail>()
    private val movieSourceData = MutableLiveData<String>()
    private val isLoading = MutableLiveData<Boolean>()

    private var currentMovieId = ""

    fun isLoading() : LiveData<Boolean> = isLoading
    fun getMovieDetail() : LiveData<MovieDetail> = movieDetail
    fun getMovieSourceData() : LiveData<String> = movieSourceData
    fun getCurrentMovieId() = currentMovieId

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getMovieDetail(id: String) {
        currentMovieId = id
        movieDetailUseCase.execute(movieId = id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.value = true
            }
            .subscribe({ response ->
                response?.let { detail ->
                    movieDetail.value = detail
                    movieSourceData.value = generateIFrame(url = detail.playUrl ?: "")
                    isLoading.value = false
                }
            }, { throwable ->
                isLoading.value = false
            }).addTo(composite = compositeDisposable)
    }

    private fun generateIFrame(url: String): String {
        val iFrame = "<iframe src=\"https://www.youtube.com/embed/$url\" " +
                "width=100% height=100% frameborder=0 style=border:none;overflow:hidden scrolling=no></iframe>"

        return "<style> body {" +
                "  background: black; }" +
                "  .video-container {" +
                "  position: absolute;" +
                "  width: 100%;" +
                "  height: 100%;" +
                "  overflow: hidden;" +
                "  top: 50%;" +
                "  left: 50%;" +
                "  transform: translate(-50%,-50%); }" +
                "</style>" + "<body> <div class=video-container > " + iFrame + " </div> </body>"
    }
}
package appsynth.galahad.mvvm.view.movie.relate

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appsynth.galahad.mvvm.R
import appsynth.galahad.mvvm.api.repo.MovieRepoImpl
import appsynth.galahad.mvvm.extenstions.getViewModel
import appsynth.galahad.mvvm.usecase.MovieDetailUseCaseImpl
import appsynth.galahad.mvvm.usecase.RelateMovieUseCaseImpl
import appsynth.galahad.mvvm.view.movie.MovieViewModel
import appsynth.galahad.mvvm.view.movie.adapter.RelateMovieAdapter
import kotlinx.android.synthetic.main.fragment_recent_movie.*

class RelateMovieFragment : Fragment() {

    companion object {
        const val TAG = "RelateMovieFragment"
        private const val MOVIE_ID = "movie_id"
        fun newInstance(movieId: String): RelateMovieFragment {
            return RelateMovieFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_ID, movieId)
                }
            }
        }
    }

    private val viewModel: MovieViewModel by lazy {
        getViewModel {
            val movieRepo = MovieRepoImpl()
            MovieViewModel(
                movieDetailUseCase = MovieDetailUseCaseImpl(movieRepo = movieRepo),
                relateMovieUseCase = RelateMovieUseCaseImpl(movieRepo = movieRepo)
            )
        }
    }

    private val relateMovieAdapter: RelateMovieAdapter by lazy {
        RelateMovieAdapter()
    }

    private var movieId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = arguments?.getString(RelateMovieFragment.MOVIE_ID) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingViewModel()
        return inflater.inflate(R.layout.fragment_recent_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        if (movieId.isNotEmpty()) {
            viewModel.getRelateMovieList(id = movieId)
        }
    }

    private fun bindingViewModel() {
        viewModel.getRelateMovieList().observe(this, Observer { movies ->
            movies?.let {
                relateMovieAdapter.setData(list = it)
                relateMovieAdapter.notifyDataSetChanged()
            }
        })

        viewModel.getRelateMovieListIsLoading().observe(this, Observer { loading ->
            Log.e("ARMTIMUS", "RelateMovieFragment loading")
            if (loading == true) {
                progressView.visibility = View.VISIBLE
                relateMovieRecycleView.visibility = View.GONE
            } else {
                progressView.visibility = View.GONE
                relateMovieRecycleView.visibility = View.VISIBLE
            }
        })

    }

    private fun initView() {
        relateMovieRecycleView.apply {
            layoutManager = GridLayoutManager(context, 3)
            this.adapter = relateMovieAdapter
        }
    }
}
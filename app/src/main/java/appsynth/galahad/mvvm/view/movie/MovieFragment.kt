package appsynth.galahad.mvvm.view.movie

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import appsynth.galahad.mvvm.R
import appsynth.galahad.mvvm.api.repo.MovieRepoImpl
import appsynth.galahad.mvvm.extenstions.getViewModel
import appsynth.galahad.mvvm.usecase.MovieDetailUseCaseImpl
import appsynth.galahad.mvvm.usecase.RelateMovieUseCaseImpl
import appsynth.galahad.mvvm.view.movie.adapter.RelateMovieAdapter
import appsynth.galahad.mvvm.view.movie.relate.RelateMovieFragment
import appsynth.galahad.mvvm.view.movie.relate.RelateMovieViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieFragment : Fragment() {

    companion object {
        const val TAG = "MovieFragment"
    }

    private val movieDetailViewModel: MovieViewModel by lazy {
        getViewModel {
            val movieRepo = MovieRepoImpl()
            MovieViewModel(
                movieDetailUseCase = MovieDetailUseCaseImpl(movieRepo = movieRepo)
            )
        }
    }

    private val relateMovieViewModel: RelateMovieViewModel by lazy {
        getViewModel {
            val movieRepo = MovieRepoImpl()
            RelateMovieViewModel(
                relateMovieUseCase = RelateMovieUseCaseImpl(movieRepo = movieRepo)
            )
        }
    }

    private val relateMovieAdapter: RelateMovieAdapter by lazy {
        RelateMovieAdapter().apply {
            listener = object : RelateMovieAdapter.RelateMovieAdapterInterface {
                override fun onItemClick(movieId: String) {
                    movieDetailViewModel.getMovieDetail(id = movieId)
                    relateMovieViewModel.getRelateMovieList(id = movieId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        movieDetailViewModel.getMovieDetail(id = "harry_1")
        relateMovieViewModel.getRelateMovieList(id = "harry_1")
    }

    private fun bindingViewModel() {
        movieDetailViewModel.isLoading().observe(this, Observer { loading ->
            if (loading == true) {
                progressView.visibility = View.VISIBLE
                contentView.visibility = View.GONE
            } else {
                progressView.visibility = View.GONE
                contentView.visibility = View.VISIBLE
            }
        })

        movieDetailViewModel.getMovieDetail().observe(this, Observer { detail ->
            detail?.let {
                movieNameTextView.text = it.name
                movieDescTextView.text = it.desc
                Glide.with(this).load(it.posterUrl).into(posterImageView)
            }
        })

        movieDetailViewModel.getMovieSourceData().observe(this, Observer { sourceData ->
            sourceData?.let {
                playerWebView.loadData(it, "text/html", "UTF-8")
            }
        })

        relateMovieViewModel.getRelateMovieList().observe(this, Observer { movies ->
            movies?.let {
                relateMovieAdapter.setData(list = it)
                relateMovieAdapter.notifyDataSetChanged()
                contentScrollView.smoothScrollTo(0, 0)
                relateMovieRecycleView.scrollToPosition(0)
            }
        })

        relateMovieViewModel.isLoading().observe(this, Observer { loading ->
            if (loading == true) {
                relateMovieProgress.visibility = View.VISIBLE
                relateMovieRecycleView.visibility = View.GONE
            } else {
                relateMovieProgress.visibility = View.GONE
                relateMovieRecycleView.visibility = View.VISIBLE
            }
        })

    }

    private fun initView() {
        relateMovieRecycleView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = relateMovieAdapter
        }

        playerWebView.apply {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            webChromeClient = WebChromeClient()
        }

        viewMoreTextView.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.add(R.id.fragment_container,
                    RelateMovieFragment.newInstance(movieId = movieDetailViewModel.getCurrentMovieId()),
                    RelateMovieFragment.TAG)
                ?.addToBackStack(MovieFragment.TAG)
                ?.commit()
        }
    }
}
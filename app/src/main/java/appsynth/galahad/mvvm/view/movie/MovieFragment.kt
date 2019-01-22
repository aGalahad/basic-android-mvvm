package appsynth.galahad.mvvm.view.movie

import android.arch.lifecycle.Observer
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import appsynth.galahad.mvvm.R
import appsynth.galahad.mvvm.api.repo.MovieRepoImpl
import appsynth.galahad.mvvm.extenstions.getViewModel
import appsynth.galahad.mvvm.usecase.MovieDetailUseCaseImpl
import appsynth.galahad.mvvm.usecase.RelateMovieUseCaseImpl
import appsynth.galahad.mvvm.view.movie.adapter.RelateMovieAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieFragment : Fragment(), RelateMovieAdapter.RelateMovieAdapterInterface {

    companion object {
        const val TAG = "MovieFragment"
    }

    private val viewModel : MovieViewModel by lazy {
        getViewModel {
            val movieRepo = MovieRepoImpl()
            MovieViewModel(
                movieDetailUseCase = MovieDetailUseCaseImpl(movieRepo = movieRepo),
                relateMovieUseCase = RelateMovieUseCaseImpl(movieRepo = movieRepo)
            )
        }
    }

    private val relateMovieAdapter : RelateMovieAdapter by lazy {
        RelateMovieAdapter()
    }

    private var isLoaded = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        bindingViewModel()

        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        viewModel.getMovieDetail(id = "harry_1")
        viewModel.getRelateMovieList(id = "harry_1")
    }

    override fun onItemClick(movieId: String) {
        viewModel.getMovieDetail(id = movieId)
        viewModel.getRelateMovieList(id = movieId)
    }

    private fun bindingViewModel() {
        viewModel.isLoading().observe(this, Observer { loading ->
            if (loading == true) {
                progressView.visibility = View.VISIBLE
                contentView.visibility = View.GONE
            } else {
                progressView.visibility = View.GONE
                contentView.visibility = View.VISIBLE
            }
        })

        viewModel.observeMovieDetail().observe(this, Observer { detail ->
            detail?.let {
                movieNameTextView.text = it.name
                movieDescTextView.text = it.desc
                Glide.with(this).load(it.posterUrl).into(posterImageView)
            }
        })

        viewModel.observeMovieSourceData().observe(this, Observer { sourceData ->
            sourceData?.let {
                playerWebView.loadData(it, "text/html", "UTF-8")
            }
        })

        viewModel.observeRelateMovieList().observe(this, Observer { movies ->
            movies?.let {
                relateMovieAdapter.setData(list = it)
                relateMovieAdapter.notifyDataSetChanged()
                contentScrollView.smoothScrollTo(0, 0)
                relateMovieRecycleView.scrollToPosition(0)
            }
        })
    }

    private fun initView() {
        relateMovieRecycleView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = relateMovieAdapter
        }

        relateMovieAdapter.listener = this

        playerWebView.apply {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            webChromeClient = WebChromeClient()

            webViewClient = object : WebViewClient() {
                // Do nothing when click on html link
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (isLoaded) {
                        val builder = CustomTabsIntent.Builder()
                        builder.addDefaultShareMenuItem()
                        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent))
                        builder.setShowTitle(true)
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(context, Uri.parse(url))
                    }
                    return true
                }

                override fun onPageFinished(webView: WebView, url: String) {
                    isLoaded = true
                }

                override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                    if (!isVisible) return
                }
            }
        }
    }
}
package appsynth.galahad.mvvm.view.movie.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appsynth.galahad.mvvm.R
import appsynth.galahad.mvvm.model.MovieDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_relate_movie.view.*

class RelateMovieAdapter : RecyclerView.Adapter<RelateMovieAdapter.ViewHolder>() {

    private val dataList = mutableListOf<MovieDetail>()

    var listener: RelateMovieAdapterInterface? = null

    interface RelateMovieAdapterInterface {
        fun onItemClick(movieId: String)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView = itemView.posterImageView!!
        val nameTextView = itemView.nameTextView!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelateMovieAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_relate_movie,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RelateMovieAdapter.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.nameTextView.text = item.name
        Glide.with(holder.itemView.context).load(item.posterUrl).into(holder.posterImageView)

        holder.itemView.setOnClickListener {
            item.id?.let { movieId ->
                listener?.onItemClick(movieId = movieId)
            }
        }
    }

    fun setData(list: List<MovieDetail>) {
        dataList.clear()
        dataList.addAll(list)
    }
}
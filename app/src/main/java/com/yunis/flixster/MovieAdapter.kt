package com.yunis.flixster

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Intent

private const val TAG = "MovieAdapter"

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    companion object {
        const val ITEM_TITLE = "item_title"
        const val ITEM_POSTER = "item_poster"
        const val ITEM_POSTER2 = "item_poster2"
        const val ITEM_OVERVIEW = "item_over_view"
        const val RB = "rb"
    }

    //Expensive operation: create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    //Cheap: simply bind data to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position: $position")

        val movie = movies[position]
        holder.bind(movie)

    }

    override fun getItemCount() = movies.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverView)


        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverView.text = movie.overview

            if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                Glide.with(context)
                    .load(movie.posterImageUrl2)
                    .placeholder(R.drawable.flicks_backdrop_placeholder)
                    .into(ivPoster);
            } else {

                Glide.with(context)
                    .load(movie.posterImageUrl)
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .into(ivPoster);
            }

            itemView.setOnClickListener {
                val position = adapterPosition
                // check position is valid
                if (position != RecyclerView.NO_POSITION) {
                    val movie: Movie = movies[position]
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(
                        ITEM_TITLE, movie.title
                    )
                    intent.putExtra(
                        ITEM_POSTER, movie.posterImageUrl
                    )
                    intent.putExtra(
                        ITEM_POSTER2, movie.posterImageUrl2
                    )
                    intent.putExtra(
                        ITEM_OVERVIEW, movie.overview
                    )
                    intent.putExtra(
                        RB, movie.voteAverage
                    )

                    context.startActivity(intent)
                }
            }
        }
    }
}



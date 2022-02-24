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

private val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    //Expensive operation: create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false)
        return ViewHolder(view)
    }

    //Cheap: simply bind data to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position: $position")

        val movie = movies[position]
        holder.bind(movie)

    }

    override fun getItemCount() = movies.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverView)


        fun bind(movie: Movie){
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
        }


        }
    }



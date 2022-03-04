package com.yunis.flixster
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val MOVIE_EXTRA = "MOVIE_EXTRA"
class MovieAdapter(private val context: Context, private val movies: List<Movie> )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val MOVIE_IMAGE_BACKDROP = 0
    private val MOVIE_DETAIL = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View
        when (viewType) {
            MOVIE_IMAGE_BACKDROP -> {
                view = inflater.inflate(R.layout.item_movie2, parent, false)
                return ViewHolder1(view)
            }
            MOVIE_DETAIL -> {
                view = inflater.inflate(R.layout.item_movie, parent, false)
                return ViewHolder2(view)
            }
            else -> {
                view = inflater.inflate(R.layout.item_movie, parent, false)

            }
        }

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val movie= movies[position]
        return if ( movie.voteAverage > 5) {
            MOVIE_IMAGE_BACKDROP
        } else {
            MOVIE_DETAIL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie= movies[position]
        when (holder.itemViewType) {
            MOVIE_IMAGE_BACKDROP -> {
                val vh1 = holder as ViewHolder1
                vh1.bind(movie)

            }
            MOVIE_DETAIL -> {
                val vh2 = holder as ViewHolder2
                vh2.bind(movie)
            }
            else -> {
                val vh = holder as ViewHolder
                with(vh) {
                    bind(movie)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverView)

        fun bind(movie:Movie){
            tvTitle.text = movie.title
            tvOverView.text = movie.overview
            if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context)
                    .load(movie.posterImageUrl)
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .into(ivPoster)
            }else {
                Glide.with(context)
                    .load(movie.posterImageUrl2)
                    .placeholder(R.drawable.flicks_backdrop_placeholder)
                    .into(ivPoster)
            }


        }

    }

    inner class ViewHolder1(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverView)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            if (tvTitle !== null) {
                tvTitle.text = movie.title
            }
            if (tvOverView !== null) {
                tvOverView.text = movie.overview
            }
            Glide.with(context)
                .load(movie.posterImageUrl2)
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(ivPoster)


        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie: Movie = movies[position]
                Toast.makeText(context,movie.title,Toast.LENGTH_SHORT).show()

                val intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra(
                    MOVIE_EXTRA, movie
                )

                context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverView)

        init{
            itemView.setOnClickListener(this)
        }
        fun bind(movie:Movie){
            tvTitle.text = movie.title
            tvOverView.text = movie.overview
            Glide.with(context)
                .load(movie.posterImageUrl)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .into(ivPoster)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val movie: Movie = movies[position]
                Toast.makeText(context,movie.title,Toast.LENGTH_SHORT).show()

                val intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra(
                    MOVIE_EXTRA, movie
                )
                context.startActivity(intent)
            }
        }
    }
}
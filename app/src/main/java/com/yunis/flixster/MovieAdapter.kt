import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunis.flixster.Movie
import com.yunis.flixster.MovieDetailsActivity
import com.yunis.flixster.R
import com.yunis.flixster.SimpleMovieAdapter

class MovieAdapter(private val context: Context, private val movies: List<Movie> )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val MOVIE_IMAGE_BACKDROP = 0
    private val MOVIE_DETAIL = 1
    companion object {
        const val ITEM_TITLE = "item_title"
        const val ITEM_POSTER = "item_poster"
        const val ITEM_POSTER2 = "item_poster2"
        const val ITEM_OVERVIEW = "item_over_view"
        const val RB = "rb"
    }
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

    inner class ViewHolder1(itemView: View): RecyclerView.ViewHolder(itemView){
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverView)
        fun bind(movie:Movie){
            if(tvTitle !== null){
                tvTitle.text = movie.title}
            if(tvOverView !== null){
                tvOverView.text = movie.overview}
            Glide.with(context)
                .load(movie.posterImageUrl2)
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(ivPoster)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val movie: Movie = movies[position]
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(
                        SimpleMovieAdapter.ITEM_TITLE, movie.title
                    )
                    intent.putExtra(
                        SimpleMovieAdapter.ITEM_POSTER, movie.posterImageUrl
                    )
                    intent.putExtra(
                        SimpleMovieAdapter.ITEM_POSTER2, movie.posterImageUrl2
                    )
                    intent.putExtra(
                        SimpleMovieAdapter.ITEM_OVERVIEW, movie.overview
                    )
                    intent.putExtra(
                        SimpleMovieAdapter.RB, movie.voteAverage
                    )

                    context.startActivity(intent)
                }
            }
        }
    }

    inner class ViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView){
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverView)
        fun bind(movie:Movie){
            tvTitle.text = movie.title
            tvOverView.text = movie.overview
            Glide.with(context)
                .load(movie.posterImageUrl)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .into(ivPoster)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val movie: Movie = movies[position]
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(
                        SimpleMovieAdapter.ITEM_TITLE, movie.title
                    )
                    intent.putExtra(
                        SimpleMovieAdapter.ITEM_POSTER, movie.posterImageUrl
                    )
                    intent.putExtra(
                        SimpleMovieAdapter.ITEM_POSTER2, movie.posterImageUrl2
                    )
                    intent.putExtra(
                        SimpleMovieAdapter.ITEM_OVERVIEW, movie.overview
                    )
                    intent.putExtra(
                        SimpleMovieAdapter.RB, movie.voteAverage
                    )

                    context.startActivity(intent)
                }
            }
        }
    }
}
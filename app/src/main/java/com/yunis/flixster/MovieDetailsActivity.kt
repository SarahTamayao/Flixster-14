package com.yunis.flixster

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeBaseActivity
import com.yunis.flixster.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : YouTubeBaseActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie

        binding.rbVoteAverage.rating = movie.voteAverage.toFloat()
        binding.tvTitle.text = movie.title
        binding.tvOverView.text = movie.overview


//        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//        } else {
//
//        }
    }

}
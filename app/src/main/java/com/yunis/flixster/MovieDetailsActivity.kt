package com.yunis.flixster

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yunis.flixster.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var voteAverage: Float? = intent.extras?.getDouble(MovieAdapter.RB)?.toFloat()

        if (voteAverage != null) {
            (if (voteAverage > 0) voteAverage / 2.0f else voteAverage.also {
                voteAverage = it
            }).let {
                binding.rbVoteAverage.rating = it
            }
        }

        binding.tvTitle.text = intent.getStringExtra(MovieAdapter.ITEM_TITLE)

        binding.tvOverView.text = intent.getStringExtra(MovieAdapter.ITEM_OVERVIEW)


        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(applicationContext)
                .load(intent.getStringExtra(MovieAdapter.ITEM_POSTER))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .into(binding.ivPoster)
        } else {
            Glide.with(applicationContext)
                .load(intent.getStringExtra(MovieAdapter.ITEM_POSTER2))
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(binding.ivPoster)
        }
    }

}
package com.yunis.flixster

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.yunis.flixster.databinding.ActivityMovieDetailsBinding
import okhttp3.Headers

private const val TAG = "MovieDetailsActivity"

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

        val client = AsyncHttpClient()

        val ai:ApplicationInfo = applicationContext?.packageManager
            ?.getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)!!
        val value = ai.metaData["keyMovieValue"]
        val key = value.toString()
        val TRAILERS_URL =  "https://api.themoviedb.org/3/movie/%d/videos?api_key=${key}"

        client.get(TRAILERS_URL.format(movie.movieId), object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG,"onFailure: $statusCode")

            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                var results = json.jsonObject.getJSONArray("results")
                Log.i(TAG,"onSuccess: JSON data $json")
                if(results.length() == 0){
                    Log.w(TAG,"No movie trailers found")
                    return
                }

                val movieTrailerJson = results.getJSONObject(0)
                val youtubeKey = movieTrailerJson.getString("key")
                initializeYoutube(youtubeKey)
            }
        })



//        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//        } else {
//
//        }
    }

    private fun initializeYoutube(youtubeKey: String) {
        val ai:ApplicationInfo = applicationContext?.packageManager
            ?.getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)!!
        val value = ai.metaData["keyValue"]
        val key = value.toString()
        binding.player.initialize(key,object: YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                Log.i(TAG,"onInitializationSuccess")
                player?.cueVideo(youtubeKey);

            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.i(TAG,"onInitializationFailure")

            }
        })
    }

}
package com.yunis.flixster

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException



private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        val movieAdapter = MovieAdapter(this,movies)
        rvMovies.adapter = movieAdapter

        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()

        val ai: ApplicationInfo = applicationContext?.packageManager
            ?.getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)!!
        val value = ai.metaData["keyMovieValue"]
        val key = value.toString()
         val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=${key}"
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG,"onFailure: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG,"onSuccess: JSON data $json")

                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list $movies")
                } catch(e: JSONException){
                    Log.e(TAG,"Encountered exception $e")
                }
            }

        })


    }
}
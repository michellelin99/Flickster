package com.example.flickster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flickster.adapters.MovieAdapter;
import com.example.flickster.models.Movie;
import com.example.flickster.models.VerticalSpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";
    private static final int VERTICAL_SPACE = 48;

    List<Movie> movies;
    MovieAdapter mAdapter;
    RecyclerView rvMovies;
    LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<>();
        rvMovies = findViewById(R.id.rv_movies);

        //create adapter
        mAdapter = new MovieAdapter(this, movies);

        //set adapater on recycler view
        rvMovies.setAdapter(mAdapter);

        //set layout manager on the recycler view
        mManager = new LinearLayoutManager((this));
        rvMovies.setLayoutManager(mManager);

        //set dividers between items -- TODO FIX
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(VERTICAL_SPACE);
        rvMovies.addItemDecoration(dividerItemDecoration);

        getMovieList();

    }


    /**
     * getMovieList
     *
     * makes GET request to the movie db and grabs all movies and add it to the
     * movies arraylist.
     */
    void getMovieList(){
        //create AsyncHttpClient to make API calls to moviedb
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());

                    //create list of movies
                    movies.addAll(Movie.fromJsonArray(results));
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit josn exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Error with API call");
            }
        });

    }

}
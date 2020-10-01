package com.example.flickster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyDPDF259vzBfAoKed_froZlJwLMOvCXa9A";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    TextView mTitle;
    RatingBar mRating;
    TextView mOverview;
    YouTubePlayerView mYoutubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //set member variables
        mTitle = findViewById(R.id.title);
        mRating = findViewById(R.id.ratingBar);
        mOverview = findViewById(R.id.overview);
        mYoutubePlayer = findViewById(R.id.player);

        //get movie obj details
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        mOverview.setText(movie.getOverview());
        mRating.setRating((float) movie.getRating());

        //make new request to Youtube endpoint for video key
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }

                    String youtubeKey = results.getJSONObject(0).getString("key");
                    initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }


    /**
     * initalizeYoutube
     *
     * sets youtubePlayer to the current video based on youtubeKey.
     * @param youtubeKey - key to trailer video
     */
    private void initializeYoutube(final String youtubeKey) {
        mYoutubePlayer.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}

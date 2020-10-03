package com.example.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    int id;
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    double rating;


    /**
     * Movie
     *
     * empty constructor for passing obj into intent
     */
    public Movie() {

    }

    /**
     * Movie
     *
     * jsonObject to extract information from.
     *
     * @param jsonObject - jsonObject containing information.
     * @throws JSONException
     */
    public Movie(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        rating = jsonObject.getDouble("vote_average");
    }

    /**
     * fromJsonArray
     *
     * creates a movie object from the movieJsonArray of JSONObjects
     *
     * @param movieJsonArray - array of JSON movie objects
     * @return - list of movies
     * @throws JSONException
     */
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < movieJsonArray.length(); ++i) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return movies;
    }


    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }
}

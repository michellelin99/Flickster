package com.example.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flickster.R;
import com.example.flickster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get movie position
        Movie movie = movies.get(position);

        //Bind movie data into VH
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mDescription;
        ImageView mPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.movie_title);
            mDescription = itemView.findViewById(R.id.movie_description);
            mPoster = itemView.findViewById(R.id.movie_poster);
        }

        public void bind(Movie movie){
            mTitle.setText(movie.getTitle());
            mDescription.setText(movie.getOverview());

            int orientation = context.getResources().getConfiguration().orientation;

            // use poster for portrait and backdrop for landscape
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context).load(movie.getPosterPath()).into(mPoster);

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context).load(movie.getBackdropPath()).into(mPoster);
            }
        }
    }

}

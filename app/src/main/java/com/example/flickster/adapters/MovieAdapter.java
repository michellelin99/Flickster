package com.example.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flickster.DetailActivity;
import com.example.flickster.R;
import com.example.flickster.models.Movie;
import com.example.flickster.models.MyAppGlide;

import org.parceler.Parcels;

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
        RelativeLayout container;
        TextView mTitle;
        TextView mDescription;
        ImageView mPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.movie_title);
            mDescription = itemView.findViewById(R.id.movie_description);
            mPoster = itemView.findViewById(R.id.movie_poster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie){
            mTitle.setText(movie.getTitle());
            mDescription.setText(movie.getOverview());

            int orientation = context.getResources().getConfiguration().orientation;

            // use poster for portrait and backdrop for landscape
            String path = "";
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                path = movie.getPosterPath();

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                path = movie.getBackdropPath();
            }

            //load iamge into poster view
            int radius = 30;
            Glide.with(context)
                    .load(path)
                    .centerCrop()
                    .transform(new RoundedCorners(radius))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(mPoster);


            //register on click listener on the whole container
            //navigates to a new activity
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }

}

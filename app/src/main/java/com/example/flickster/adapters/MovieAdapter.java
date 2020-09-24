package com.example.flickster.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter {
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mDescription;
        ImageView mPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.movie_title);
        }
    }

}

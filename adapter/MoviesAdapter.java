package com.section27.paginglibrarytest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.section27.paginglibrarytest.databinding.SingleMovieItemBinding;
import com.section27.paginglibrarytest.model.Movie;

public class MoviesAdapter extends PagingDataAdapter<Movie,MoviesAdapter.MovieViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int MOVIE_ITEM = 1;
    private RequestManager glide;

    public MoviesAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback, RequestManager glide) {
        super(diffCallback);
        this.glide = glide;
    }

    @NonNull
    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //create movies Binding
        SingleMovieItemBinding movieItemBinding = SingleMovieItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false);

        //create movie view holder with data binding
        MovieViewHolder movieViewHolder = new MovieViewHolder( movieItemBinding);

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MovieViewHolder holder, int position) {
        Movie currentMovie = getItem(position);
        if (currentMovie != null) {
            holder.movieItemBinding.textViewRating.setText(String.valueOf(currentMovie.getVoteAverage()));
            //set img
            glide.load("https://image.tmdb.org/t/p/w500" + currentMovie.getPosterPath())
                    .into(holder.movieItemBinding.imageViewMovie);
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{

        SingleMovieItemBinding movieItemBinding;
        public MovieViewHolder(@NonNull SingleMovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.movieItemBinding = movieItemBinding;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // if position == getItemCount() is  true means there are items set to 1, otherwise set to 0 as Loading
        return position == getItemCount() ? MOVIE_ITEM : LOADING_ITEM;

    }

}

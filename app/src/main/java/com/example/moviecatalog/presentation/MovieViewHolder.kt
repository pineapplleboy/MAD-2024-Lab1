package com.example.moviecatalog.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.databinding.MoviePreviewBinding
import com.example.moviecatalog.domain.model.MovieElement


class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = MoviePreviewBinding.bind(view)

    init{}

    fun bind(movie: MovieElement) = with(binding){
        Glide.with(view)
            .load(movie.poster)
            .into(binding.moviePoster)
    }

}
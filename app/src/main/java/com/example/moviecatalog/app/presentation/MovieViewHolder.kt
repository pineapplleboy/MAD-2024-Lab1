package com.example.moviecatalog.app.presentation

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.databinding.MoviePreviewBinding
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.app.presentation.ui.activity.MovieDetailsActivity


class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = MoviePreviewBinding.bind(view)
    private var id: String? = null

    init{
        binding.moviePoster.setOnClickListener{
            val intent = Intent(view.context, MovieDetailsActivity::class.java).apply {
                putExtra("MOVIE_ID", id)
            }

            view.context.startActivity(intent)
        }
    }

    fun bind(movie: MovieElement) = with(binding){

        id = movie.id

        Glide.with(view)
            .load(movie.poster)
            .into(binding.moviePoster)
    }

}
package com.example.moviecatalog.app.presentation.viewholder

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.app.presentation.ui.activity.MovieDetailsActivity
import com.example.moviecatalog.databinding.MovieCardBinding
import com.example.moviecatalog.domain.model.MovieElement

class MovieCardViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private val binding = MovieCardBinding.bind(view)
    private var movie: MovieElement? = null

    init {
        binding.poster.setOnClickListener{
            val intent = Intent(view.context, MovieDetailsActivity::class.java).apply {
                putExtra("MOVIE_ID", movie?.id)
            }

            view.context.startActivity(intent)
        }
    }

    fun bind(movieElement: MovieElement) = with(binding){

        movie = movieElement

        Glide.with(view)
            .load(movieElement.poster)
            .into(poster)
    }
}
package com.example.moviecatalog.app.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.databinding.FavoritePreviewBinding
import com.example.moviecatalog.domain.model.MovieElement

class FavoriteViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private val binding = FavoritePreviewBinding.bind(view)
    private var movie: MovieElement? = null

    init {

    }

    fun bind(movieElement: MovieElement) = with(binding){

        movie = movieElement

        Glide.with(view)
            .load(movieElement.poster)
            .into(poster)
    }
}
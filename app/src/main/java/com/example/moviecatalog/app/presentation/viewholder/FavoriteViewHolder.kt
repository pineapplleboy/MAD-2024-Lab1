package com.example.moviecatalog.app.presentation.viewholder

import android.content.Intent
import android.view.View
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.app.presentation.ui.activity.MovieDetailsActivity
import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.databinding.FavoritePreviewBinding
import com.example.moviecatalog.domain.model.MovieElement

class FavoriteViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private val binding = FavoritePreviewBinding.bind(view)
    private var movie: MovieElement? = null

    init {
        binding.poster.setOnClickListener{
            val intent = Intent(view.context, MovieDetailsActivity::class.java).apply {
                putExtra("MOVIE_ID", movie?.id)
            }

            view.context.startActivity(intent)
        }
    }

    fun bind(movieElement: MovieElement, isScaled: Boolean) = with(binding){

        movie = movieElement

        Glide.with(view)
            .load(movieElement.poster)
            .into(poster)

        if(isScaled){
            itemView.scaleX = 1.067f
            itemView.scaleY = 1.067f
        }
        else{
            itemView.scaleX = 1f
            itemView.scaleY = 1f
        }
    }
}
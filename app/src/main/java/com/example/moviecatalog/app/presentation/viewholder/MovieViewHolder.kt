package com.example.moviecatalog.app.presentation.viewholder

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.databinding.MoviePreviewBinding
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.app.presentation.ui.activity.MovieDetailsActivity
import com.example.moviecatalog.domain.usecase.movies.GetMovieRatingUseCase


class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = MoviePreviewBinding.bind(view)
    private var id: String? = null

    init {
        binding.moviePoster.setOnClickListener {
            val intent = Intent(view.context, MovieDetailsActivity::class.java).apply {
                putExtra("MOVIE_ID", id)
            }

            view.context.startActivity(intent)
        }
    }

    fun bind(movie: MovieElement) = with(binding) {

        id = movie.id

        Glide.with(view)
            .load(movie.poster)
            .into(moviePoster)

        val rating = GetMovieRatingUseCase().execute(movie)

        movieRating.text = rating
//        movieRating.background = createRoundedBackground(getRatingColor(rating.toFloat()))
    }

    private fun getRatingColor(rating: Float): Int {
        val normalizedRating = rating / 10f

        val red = (255 * (1 - normalizedRating)).toInt()
        val green = (255 * normalizedRating).toInt()

        return Color.rgb(red, green, 0)
    }

    private fun createRoundedBackground(color: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.cornerRadius = 4 * Resources.getSystem().displayMetrics.density
        drawable.setColor(color)
        return drawable
    }
}
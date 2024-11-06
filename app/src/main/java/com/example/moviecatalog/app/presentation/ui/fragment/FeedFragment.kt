package com.example.moviecatalog.app.presentation.ui.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.R
import com.example.moviecatalog.app.app.AppComponent
import com.example.moviecatalog.app.presentation.adapter.FavoriteAdapter
import com.example.moviecatalog.app.presentation.adapter.MovieCardAdapter
import com.example.moviecatalog.app.presentation.viewmodel.FeedViewModel
import com.example.moviecatalog.databinding.FragmentFeedBinding
import com.example.moviecatalog.domain.model.MovieElement
import com.google.android.material.chip.Chip
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var appComponent: AppComponent
    private lateinit var vm: FeedViewModel

    private var currPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        appComponent = AppComponent(binding.root.context)
        vm = appComponent.provideFeedViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getFavoriteGenres()

        val movieCardAdapter = MovieCardAdapter()

        val manager = CardStackLayoutManager(context, object : CardStackListener {

            override fun onCardSwiped(direction: Direction) {
                when (direction) {
                    Direction.Right -> {
                        vm.getNextMovie()
                        vm.movies.value?.get(currPosition)?.let { vm.addToFavorites(it.id) }
                    }
                    Direction.Left -> {
                        vm.getNextMovie()
                    }

                    Direction.Top -> return
                    Direction.Bottom -> return
                }
            }

            override fun onCardDragging(direction: Direction, ratio: Float) {

                val cardView = binding.movieStackView.layoutManager?.findViewByPosition(currPosition)

                val alpha = (ratio * 200).toInt().coerceIn(0, 200)

                val poster = cardView?.findViewById<ImageView>(R.id.poster)
                val icon = cardView?.findViewById<ImageView>(R.id.icon)

                when (direction) {
                    Direction.Left -> {
                        poster?.setColorFilter(Color.argb(alpha, 51, 51, 51))
                        icon?.setImageResource(R.drawable.dislike)
                    }
                    Direction.Right -> {
                        val gradientDrawable = GradientDrawable(
                            GradientDrawable.Orientation.LEFT_RIGHT,
                            intArrayOf(Color.argb(alpha, 223, 40, 0), Color.argb(alpha, 255, 102, 51))
                        )
                        poster?.foreground = gradientDrawable
                        icon?.setImageResource(R.drawable.like)
                    }
                    else -> {
                        poster?.clearColorFilter()
                        poster?.foreground = null
                        icon?.setImageDrawable(null)
                    }
                }
            }

            override fun onCardRewound() {}

            override fun onCardCanceled() {
                val cardView = binding.movieStackView.layoutManager?.findViewByPosition(currPosition)

                cardView?.findViewById<ImageView>(R.id.poster)?.apply {
                    clearColorFilter()
                    foreground = null
                }

                cardView?.findViewById<ImageView>(R.id.icon)?.setImageDrawable(null)
            }

            override fun onCardAppeared(view: View, position: Int) {

                currPosition = position
                val movie = vm.movies.value?.get(position)
                if(movie != null){
                    displayMovie(movie)
                }

                view.findViewById<ImageView>(R.id.poster).apply {
                    clearColorFilter()
                    foreground = null
                }

                view.findViewById<ImageView>(R.id.icon)?.setImageDrawable(null)
            }

            override fun onCardDisappeared(view: View, position: Int) {
                view.findViewById<ImageView>(R.id.poster).apply {
                    clearColorFilter()
                    foreground = null
                }

                view.findViewById<ImageView>(R.id.icon)?.setImageDrawable(null)
            }
        })

        binding.movieStackView.layoutManager = manager
        binding.movieStackView.adapter = movieCardAdapter

        vm.getNextMovie()
        vm.getNextMovie()
        (binding.movieStackView.adapter as MovieCardAdapter).submitList(vm.movies.value)

        vm.movies.observe(viewLifecycleOwner){
            (binding.movieStackView.adapter as MovieCardAdapter).submitList(it)

            if (it.isNotEmpty() && currPosition == 0) {
                displayMovie(it[0])
            }
        }
    }

    private fun displayMovie(movie: MovieElement){
        binding.name.text = movie.name
        binding.info.text = "${movie.country}•${movie.year}"

        binding.genres.removeAllViews()

        for(genre in movie.genres ?: listOf()){

            val chip = Chip(this.context).apply {
                text = genre.name
                isCheckable = false
                isClickable = false
                setTextColor(resources.getColor(R.color.white, null))
                setPadding(12, 4, 12, 4)
                textSize = 16f
                chipStrokeWidth = 0f

                if(vm.favoriteGenres.value?.find {it.id == genre.id} == null){
                    setChipBackgroundColorResource(R.color.dark_faded)
                }
                else{
                    setChipBackgroundColorResource(R.color.orange_dark)
                }
            }

            binding.genres.addView(chip)
        }
    }
}
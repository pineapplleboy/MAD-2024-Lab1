package com.example.moviecatalog.app.presentation.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.app.presentation.adapter.FavoriteAdapter
import com.example.moviecatalog.app.presentation.adapter.MovieListAdapter
import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.app.presentation.viewmodel.MoviesViewModel
import com.example.moviecatalog.databinding.FragmentMoviesBinding
import com.example.moviecatalog.domain.model.MovieElement
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private val vm by viewModel<MoviesViewModel>()
    private val favoritesVM by viewModel<FavoritesViewModel>()
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentTopMovie = -1
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var binding: FragmentMoviesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFavoritesRecycler()
        setupTopMovieRotation()
        observeDataChanges()
    }

    private fun setupRecyclerView() {
        val movieAdapter = MovieListAdapter()
        binding.moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 3)
            adapter = movieAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val threshold = 1
                    if (totalItemCount <= lastVisibleItemPosition + threshold) {
                        vm.loadNextPage()
                    }
                }
            })
        }
    }

    private fun setupFavoritesRecycler() {
        val favoriteAdapter = FavoriteAdapter()
        binding.favoritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteAdapter
        }

        favoritesVM.getFavoriteMovies()
        favoritesVM.favoriteMovies.observe(this) {
            (binding.favoritesRecyclerView.adapter as FavoriteAdapter).submitList(it)
        }
    }

    private fun setupTopMovieRotation() {
        val progressBarArray = arrayOf(
            binding.progressBar1,
            binding.progressBar2,
            binding.progressBar3,
            binding.progressBar4,
            binding.progressBar5
        )

        handler = Handler()
        runnable = Runnable {
            currentTopMovie = if (currentTopMovie >= (vm.topMovies.value?.size
                    ?: 0) - 1
            ) 0 else (currentTopMovie + 1)
            if (currentTopMovie == 0) resetProgressBars(progressBarArray)
            showTopMovie(vm.topMovies.value?.get(currentTopMovie))
            startProgressBar(progressBarArray[currentTopMovie], currentTopMovie == 4)
            handler.postDelayed(runnable, 5000)
        }
    }

    private fun resetProgressBars(progressBarArray: Array<ProgressBar>) {
        for (bar in progressBarArray) {
            bar.progress = 0
        }
    }

    private fun observeDataChanges() {
        vm.topMovies.observe(this) {
            handler.post(runnable)
        }
        vm.movies.observe(this) {
            (binding.moviesRecyclerView.adapter as MovieListAdapter).submitList(it)
        }
    }

    private fun showTopMovie(movie: MovieElement?) {
        movie?.let {
            Glide.with(this).load(it.poster).into(binding.topMovieImage)
            binding.topMovieName.text = it.name
        }
    }

    private fun startProgressBar(progressBar: ProgressBar, resetOnFinish: Boolean = false) {
        countDownTimer = object : CountDownTimer(5000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = progressBar.max - (millisUntilFinished / 50).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                progressBar.progress = if (resetOnFinish) 0 else progressBar.max
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
        countDownTimer.cancel()
        currentTopMovie = -1
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        countDownTimer.cancel()
    }
}
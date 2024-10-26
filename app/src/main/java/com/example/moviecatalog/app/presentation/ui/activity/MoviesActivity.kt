package com.example.moviecatalog.app.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.ActivityMoviesBinding
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.app.presentation.MovieListAdapter
import com.example.moviecatalog.app.presentation.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity() {

    private val vm by viewModel<MoviesViewModel>()
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentTopMovie = -1
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var binding: ActivityMoviesBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSystemBars()
        setupRecyclerView()
        setupNavigation()
        setupTopMovieRotation()
        observeDataChanges()
    }

    private fun setupSystemBars() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun setupRecyclerView() {
        val movieAdapter = MovieListAdapter()
        binding.moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MoviesActivity, 3)
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

    private fun setupNavigation() {
        binding.profileNavigation.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.libraryNavigation.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
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
            currentTopMovie = if (currentTopMovie >= (vm.topMovies.value?.size ?: 0) - 1) 0 else (currentTopMovie + 1)
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

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        countDownTimer.cancel()
    }
}

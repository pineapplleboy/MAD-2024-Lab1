package com.example.moviecatalog.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Movie
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.R
import com.example.moviecatalog.data.api.MovieApiInstance
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.databinding.ActivityMoviesBinding
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.usecase.GetMoviesPageUseCase
import com.example.moviecatalog.domain.usecase.GetTopMoviesUseCase
import com.example.moviecatalog.presentation.MovieListAdapter
import com.example.moviecatalog.presentation.ui.compose.MoviesDetails
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoviesActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentTopMovie = -1
    private lateinit var topMovies: List<MovieElement>

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var binding: ActivityMoviesBinding

    private var currentMoviePage = 1
    private val movies = mutableListOf<MovieElement>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        val movieAdapter = MovieListAdapter()
        val movieRecyclerView = binding.moviesRecyclerView.apply{
            layoutManager = GridLayoutManager(this.context, 3)
            adapter = movieAdapter
        }

        binding.profileNavigation.setOnClickListener{

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val progressBar: Array<ProgressBar> = arrayOf(
            binding.progressBar1,
            binding.progressBar2,
            binding.progressBar3,
            binding.progressBar4,
            binding.progressBar5
        )

        handler = Handler()

        runnable = Runnable {
            currentTopMovie = if (currentTopMovie >= topMovies.size - 1) 0 else (currentTopMovie + 1)

            if(currentTopMovie == 0){
                for (bar in progressBar){
                    bar.progress = 0
                }
            }
            showTopMovie(
                movie = topMovies[currentTopMovie]
            )

            startProgressBar(progressBar[currentTopMovie], (currentTopMovie == 4))

            handler.postDelayed(runnable, 5000)
        }

        val movieApi = MovieApiInstance.createApi()
        val repository = MovieRepositoryImpl(movieApi)

        val getTopMoviesUseCase = GetTopMoviesUseCase(repository)
        getTopMoviesUseCase.execute { movies ->
            topMovies = movies
            handler.post(runnable)
        }

        val getMoviesByPageUseCase = GetMoviesPageUseCase(repository)
        getMoviesByPageUseCase.execute(1) {
            it.movies?.let { it1 -> movies.addAll(it1) }
            movieAdapter.submitList(movies)
        }

        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                val threshold = 1
                if (totalItemCount <= lastVisibleItemPosition + threshold) {
                    currentMoviePage++
                    getMoviesByPageUseCase.execute(currentMoviePage) {
                        it.movies?.let {
                                it1 -> movies.addAll(it1)
                            movieAdapter.submitList(movies.toList())
                            movieAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

    private fun startProgressBar(progressBar: ProgressBar, makeNullOnFinish: Boolean = false) {

        countDownTimer = object : CountDownTimer(5000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = progressBar.max - (millisUntilFinished / 50).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                if(!makeNullOnFinish){
                    progressBar.progress = progressBar.max
                }
                else{
                    progressBar.progress = 0
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        countDownTimer.cancel()
    }

    private fun showTopMovie(
        movie: MovieElement
    ){
        Glide.with(this)
            .load(movie.poster)
            .into(binding.topMovieImage)

        binding.topMovieName.text = movie.name
    }
}
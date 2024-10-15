package com.example.moviecatalog.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.moviecatalog.R
import com.example.moviecatalog.data.api.MovieApiInstance
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.model.MovieElement
import com.example.moviecatalog.domain.usecase.GetTopMoviesUseCase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoviesActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentTopMovie = -1
    private lateinit var topMovies: List<MovieElement>
    private lateinit var topMovieImage: ImageView
    private lateinit var topMovieName: TextView

    private lateinit var countDownTimer: CountDownTimer

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movies)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigationPanel)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_movies -> {
                    true
                }
                R.id.navigation_feed -> {
                    true
                }
                R.id.navigation_library -> {
                    true
                }
                else -> false
            }
        }

        val progressBar: Array<ProgressBar> = arrayOf(
            findViewById(R.id.progressBar1),
            findViewById(R.id.progressBar2),
            findViewById(R.id.progressBar3),
            findViewById(R.id.progressBar4),
            findViewById(R.id.progressBar5)
        )

        topMovieImage = findViewById(R.id.topMovieImage)
        topMovieName = findViewById(R.id.topMovieName)

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
            .into(topMovieImage)

        topMovieName.text = movie.name
    }
}
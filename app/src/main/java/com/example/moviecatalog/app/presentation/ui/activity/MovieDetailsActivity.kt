package com.example.moviecatalog.app.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.moviecatalog.R
import com.example.moviecatalog.app.app.AppComponent
import com.example.moviecatalog.app.presentation.ui.activity.ui.theme.MovieCatalogTheme
import com.example.moviecatalog.app.presentation.ui.compose.MoviesDetailsScreen
import com.example.moviecatalog.app.presentation.viewmodel.MovieDetailsViewModel

class MovieDetailsActivity : ComponentActivity() {

    private lateinit var appComponent: AppComponent
    private lateinit var vm: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCatalogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.dark)
                ) {

                    appComponent = AppComponent(this)
                    vm = appComponent.provideMovieDetailsViewModel()

                    val w = window
                    w.setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    )

                    val movieId = remember { intent.getStringExtra("MOVIE_ID") }

                    if (movieId != null) {
                        vm.getMovieDetails(movieId)
                    }

                    MoviesDetailsScreen(
                        context = this,
                        vm = vm
                    )
                }
            }
        }
    }
}
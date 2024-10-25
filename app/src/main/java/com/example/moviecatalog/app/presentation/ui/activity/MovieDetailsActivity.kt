package com.example.moviecatalog.app.presentation.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.FavoritesRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.usecase.AddToFavoritesUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.app.presentation.ui.activity.ui.theme.MovieCatalogTheme
import com.example.moviecatalog.app.presentation.ui.compose.MoviesDetails
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import kotlinx.coroutines.launch


class MovieDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCatalogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.dark)
                ) {
                    val w = window
                    w.setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    )

                    val movieId = intent.getStringExtra("MOVIE_ID")
                    val movieRepository = MovieRepositoryImpl(MovieCatalogApiInstance.createApi(
                        AuthPreferences(this)
                    ))
                    val getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)

                    val favoritesApi = MovieCatalogApiInstance.createApi(
                        AuthPreferences(this)
                    )
                    val favoritesRepository = FavoritesRepositoryImpl(favoritesApi)
                    val addToFavoritesUseCase = AddToFavoritesUseCase(favoritesRepository)

                    var movieLoaded by remember {
                        mutableStateOf(false)
                    }
                    var movie by remember {
                        mutableStateOf<MovieDetails?>(null)
                    }

                    if(movieId != null){
                        if(!movieLoaded){
                            LaunchedEffect(movieId) {
                                val result = getMovieDetailsUseCase.execute(movieId)
                                result.onSuccess {
                                    movie = it
                                    movieLoaded = true
                                }
                            }
                        }
                        else movie?.let {
                            MoviesDetails(it, this,
                                addToFavorites = {
                                    lifecycleScope.launch {
                                        addToFavoritesUseCase.execute(it)
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}
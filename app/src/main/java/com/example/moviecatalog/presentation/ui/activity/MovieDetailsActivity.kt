package com.example.moviecatalog.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.moviecatalog.R
import com.example.moviecatalog.data.api.FavoritesApiInstance
import com.example.moviecatalog.data.api.MovieApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.FavoritesRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.domain.model.MovieDetails
import com.example.moviecatalog.domain.usecase.AddToFavoritesUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.presentation.ui.activity.ui.theme.MovieCatalogTheme
import com.example.moviecatalog.presentation.ui.compose.MoviesDetails


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
                    val movieRepository = MovieRepositoryImpl(MovieApiInstance.createApi())
                    val getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)

                    val favoritesApi = FavoritesApiInstance.createApi(
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
                            getMovieDetailsUseCase.execute(movieId){
                                movie = it
                                movieLoaded = true
                            }
                        }
                        else movie?.let {
                            MoviesDetails(it, this,
                                addToFavorites = {
                                    addToFavoritesUseCase.execute(it){

                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}
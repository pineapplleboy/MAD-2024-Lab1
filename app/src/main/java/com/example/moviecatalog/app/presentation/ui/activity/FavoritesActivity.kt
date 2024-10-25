package com.example.moviecatalog.app.presentation.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.repository.FavoritesRepositoryImpl
import com.example.moviecatalog.databinding.ActivityFavoritesBinding
import com.example.moviecatalog.domain.usecase.GetFavoritesUseCase
import com.example.moviecatalog.app.presentation.ui.compose.Favorites
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import kotlinx.coroutines.launch

private lateinit var binding: ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val favoritesRepository = FavoritesRepositoryImpl(MovieCatalogApiInstance.createApi(
            AuthPreferences(this)
        ))

        val getFavoritesUseCase = GetFavoritesUseCase(favoritesRepository)

        lifecycleScope.launch {

            val result = getFavoritesUseCase.execute()
            result.onSuccess {
                binding.favoritesScreen.setContent {
                    Favorites(
                        genres = listOf(),
                        movies = it
                    )
                }
            }
        }
    }
}
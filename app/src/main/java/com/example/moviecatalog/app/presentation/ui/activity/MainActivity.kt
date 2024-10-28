package com.example.moviecatalog.app.presentation.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.ui.fragment.FavoritesFragment
import com.example.moviecatalog.app.presentation.ui.fragment.MoviesFragment
import com.example.moviecatalog.app.presentation.ui.fragment.ProfileFragment
import com.example.moviecatalog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val profileFragment = ProfileFragment()
        val moviesFragment = MoviesFragment()
        val favoritesFragment = FavoritesFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.navigationScreen, profileFragment)
            .addToBackStack(null)
            .commit()

        binding.moviesNavigation.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.navigationScreen, moviesFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.profileNavigation.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.navigationScreen, profileFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.libraryNavigation.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.navigationScreen, favoritesFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
package com.example.moviecatalog.app.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalog.app.app.AppComponent
import com.example.moviecatalog.app.presentation.ui.compose.Favorites
import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var appComponent: AppComponent
    private lateinit var vm: FavoritesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        appComponent = AppComponent(binding.root.context)
        vm = appComponent.provideFavoritesViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getFavoriteMovies()
        vm.getFavoriteGenres()

        binding.favoritesScreen.setContent {

            Favorites(
                vm = vm
            )
        }
    }
}
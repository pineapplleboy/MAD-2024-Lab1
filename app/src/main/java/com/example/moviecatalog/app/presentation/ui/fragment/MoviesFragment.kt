package com.example.moviecatalog.app.presentation.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Rect
import android.graphics.Shader
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.R
import com.example.moviecatalog.app.app.AppComponent
import com.example.moviecatalog.app.presentation.adapter.FavoriteAdapter
import com.example.moviecatalog.app.presentation.adapter.MovieListAdapter
import com.example.moviecatalog.app.presentation.ui.activity.MovieDetailsActivity
import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.app.presentation.viewmodel.MoviesViewModel
import com.example.moviecatalog.databinding.FragmentMoviesBinding
import com.example.moviecatalog.domain.model.MovieElement
import com.google.android.material.chip.Chip
import com.grzegorzojdana.spacingitemdecoration.Spacing
import com.grzegorzojdana.spacingitemdecoration.SpacingItemDecoration

class MoviesFragment : Fragment() {

    private lateinit var appComponent: AppComponent
    private lateinit var vm: MoviesViewModel
    private lateinit var favoritesVM: FavoritesViewModel

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

        appComponent = AppComponent(binding.root.context)
        vm = appComponent.provideMoviesViewModel()
        favoritesVM = appComponent.provideFavoritesViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFavoritesRecycler()
        setupTopMovieRotation()
        observeDataChanges()
        setRandomMovieListener()
        setWatchAllFavoritesButton()
        setGradientText(binding.favoriteMoviesText)
        setGradientText(binding.allMoviesText)
    }

    private fun setGradientText(textView: TextView){
        val paint = textView.paint
        val width = paint.measureText(textView.text.toString())

        val shader = LinearGradient(0f, 0f, width, textView.textSize,
            intArrayOf(Color.parseColor("#DF2800"), Color.parseColor("#FF6633")),
            null, Shader.TileMode.CLAMP)

        paint.shader = shader
    }

    private fun setRandomMovieListener(){
        binding.randomMovieButton.setOnClickListener{
            vm.movies.value?.random()?.let { it1 -> startMovieDetailsActivity(it1.id) }
        }
    }

    private fun setWatchAllFavoritesButton(){
        binding.watchAllFavoritesText.setOnClickListener{
            val favoritesFragment = FavoritesFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.navigationScreen, favoritesFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupRecyclerView() {
        val movieAdapter = MovieListAdapter()
        binding.moviesRecyclerView.apply {

            layoutManager = GridLayoutManager(this.context, 3)
            adapter = movieAdapter

            val spacingItemDecoration = SpacingItemDecoration(
                Spacing(
                    horizontal = 8.dpToPx(this.context),
                    vertical = 8.dpToPx(this.context)
                )
            )

            addItemDecoration(
                spacingItemDecoration
            )

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

    private fun Int.dpToPx(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
        ).toInt()
    }

    private fun setupFavoritesRecycler() {
        if(favoritesVM.favoriteMovies.value?.isNotEmpty() == true){

            binding.favorites.visibility = View.VISIBLE

            val favoriteAdapter = FavoriteAdapter()
            binding.favoritesRecyclerView.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = favoriteAdapter

                var firstVisiblePosition = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                favoriteAdapter.setScaledPosition(firstVisiblePosition)


                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val newPosition = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                        if(firstVisiblePosition != newPosition){
                            firstVisiblePosition = newPosition
                            favoriteAdapter.setScaledPosition(firstVisiblePosition)
                        }
                    }
                })

            }

            favoritesVM.getFavoriteMovies()
            favoritesVM.favoriteMovies.observe(viewLifecycleOwner) {
                (binding.favoritesRecyclerView.adapter as FavoriteAdapter).submitList(it)
            }
        }

        else{
            binding.favorites.visibility = View.GONE
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
        vm.topMovies.observe(viewLifecycleOwner) {
            handler.post(runnable)
        }
        vm.movies.observe(viewLifecycleOwner) {
            (binding.moviesRecyclerView.adapter as MovieListAdapter).submitList(it)
        }
    }

    private fun showTopMovie(movie: MovieElement?) {
        movie?.let {

            vm.getFavoriteGenres()

            Glide.with(this).load(it.poster).into(binding.topMovieImage)

            binding.topMovieName.text = it.name

            binding.watchTopMovie.setOnClickListener {
                startMovieDetailsActivity(movie.id)
            }

            binding.genres.removeAllViews()

            for(genre in movie.genres ?: listOf()){

                val chip = Chip(this.context).apply {
                    text = genre.name
                    isCheckable = false
                    isClickable = false
                    setTextColor(resources.getColor(R.color.white, null))
                    setPadding(12, 4, 12, 4)
                    textSize = 16f
                    chipStrokeWidth = 0f

                    if(vm.favoriteGenres.value?.find {it.id == genre.id} == null){
                        setChipBackgroundColorResource(R.color.dark_faded)
                    }
                    else{
                        setChipBackgroundColorResource(R.color.orange_dark)
                    }
                }

                binding.genres.addView(chip)
            }
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

    private fun startMovieDetailsActivity(id: String){
        val intent = Intent(this.context, MovieDetailsActivity::class.java).apply {
            putExtra("MOVIE_ID", id)
        }

        startActivity(intent)
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
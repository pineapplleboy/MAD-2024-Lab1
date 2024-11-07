package com.example.moviecatalog.app.app

import android.content.Context
import com.example.moviecatalog.data.api.MovieCatalogApi
import com.example.moviecatalog.data.api.MovieCatalogApiInstance
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.preferences.FriendsPreferences
import com.example.moviecatalog.data.preferences.GenresPreferences
import com.example.moviecatalog.data.preferences.MoviesPreferences
import com.example.moviecatalog.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.data.repository.FavoriteGenresRepositoryImpl
import com.example.moviecatalog.data.repository.FavoritesRepositoryImpl
import com.example.moviecatalog.data.repository.FriendsRepositoryImpl
import com.example.moviecatalog.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.data.repository.UserProfileRepositoryImpl
import com.example.moviecatalog.domain.repository.AuthRepository
import com.example.moviecatalog.domain.repository.FavoriteGenresRepository
import com.example.moviecatalog.domain.repository.FavoritesRepository
import com.example.moviecatalog.domain.repository.FriendsRepository
import com.example.moviecatalog.domain.repository.MovieRepository
import com.example.moviecatalog.domain.repository.UserProfileRepository
import com.example.moviecatalog.domain.usecase.favorites.AddFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.favorites.AddToFavoritesUseCase
import com.example.moviecatalog.domain.usecase.favorites.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.favorites.DeleteFromFavoritesUseCase
import com.example.moviecatalog.domain.usecase.favorites.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.favorites.GetFavoritesUseCase
import com.example.moviecatalog.domain.usecase.feed.GetNextUseCase
import com.example.moviecatalog.domain.usecase.friends.AddFriendUseCase
import com.example.moviecatalog.domain.usecase.friends.DeleteFriendUseCase
import com.example.moviecatalog.domain.usecase.friends.GetFriendsUseCase
import com.example.moviecatalog.domain.usecase.movies.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.movies.GetMoviesPageUseCase
import com.example.moviecatalog.domain.usecase.movies.GetTopMoviesUseCase
import com.example.moviecatalog.domain.usecase.profile.GetUserProfileUseCase
import com.example.moviecatalog.domain.usecase.profile.LogOutUseCase
import com.example.moviecatalog.domain.usecase.profile.SignInUseCase
import com.example.moviecatalog.domain.usecase.profile.SignUpUseCase
import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.app.presentation.viewmodel.FeedViewModel
import com.example.moviecatalog.app.presentation.viewmodel.FriendsViewModel
import com.example.moviecatalog.app.presentation.viewmodel.LaunchViewModel
import com.example.moviecatalog.app.presentation.viewmodel.MovieDetailsViewModel
import com.example.moviecatalog.app.presentation.viewmodel.MoviesViewModel
import com.example.moviecatalog.app.presentation.viewmodel.ProfileViewModel
import com.example.moviecatalog.app.presentation.viewmodel.SignInViewModel
import com.example.moviecatalog.app.presentation.viewmodel.SignUpViewModel
import com.example.moviecatalog.data.repository.ReviewRepositoryImpl
import com.example.moviecatalog.domain.repository.ReviewRepository
import com.example.moviecatalog.domain.usecase.review.AddReviewUseCase
import com.example.moviecatalog.domain.usecase.review.DeleteReviewUseCase
import com.example.moviecatalog.domain.usecase.review.EditReviewUseCase

class AppComponent(private val context: Context) {

    private val authPreferences: AuthPreferences by lazy { AuthPreferences(context) }
    private val genresPreferences: GenresPreferences by lazy { GenresPreferences(context) }
    private val friendsPreferences: FriendsPreferences by lazy { FriendsPreferences(context) }
    private val moviesPreferences: MoviesPreferences by lazy { MoviesPreferences(context) }

    private val movieCatalogApi: MovieCatalogApi by lazy {
        MovieCatalogApiInstance.createApi(
            authPreferences
        )
    }

    private val movieRepository: MovieRepository by lazy { MovieRepositoryImpl(movieCatalogApi) }
    private val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(
            movieCatalogApi,
            authPreferences
        )
    }
    private val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepositoryImpl(
            movieCatalogApi
        )
    }
    private val userProfileRepository: UserProfileRepository by lazy {
        UserProfileRepositoryImpl(
            movieCatalogApi
        )
    }
    private val favoriteGenresRepository: FavoriteGenresRepository by lazy {
        FavoriteGenresRepositoryImpl(
            genresPreferences
        )
    }
    private val friendsRepository: FriendsRepository by lazy {
        FriendsRepositoryImpl(
            friendsPreferences
        )
    }
    private val reviewRepository: ReviewRepository by lazy { ReviewRepositoryImpl(movieCatalogApi) }

    private val getTopMoviesUseCase: GetTopMoviesUseCase by lazy {
        GetTopMoviesUseCase(
            movieRepository
        )
    }
    private val getMoviesPageUseCase: GetMoviesPageUseCase by lazy {
        GetMoviesPageUseCase(
            movieRepository
        )
    }
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase by lazy {
        GetMovieDetailsUseCase(
            movieRepository
        )
    }
    private val addToFavoritesUseCase: AddToFavoritesUseCase by lazy {
        AddToFavoritesUseCase(
            favoritesRepository
        )
    }
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase by lazy {
        DeleteFromFavoritesUseCase(
            favoritesRepository
        )
    }
    private val getFavoritesUseCase: GetFavoritesUseCase by lazy {
        GetFavoritesUseCase(
            favoritesRepository
        )
    }

    private val getUserProfileUseCase: GetUserProfileUseCase by lazy {
        GetUserProfileUseCase(
            userProfileRepository
        )
    }
    private val logOutUseCase: LogOutUseCase by lazy {
        LogOutUseCase(
            authRepository,
            friendsPreferences,
            genresPreferences,
            moviesPreferences
        )
    }
    private val signInUseCase: SignInUseCase by lazy { SignInUseCase(authRepository) }
    private val signUpUseCase: SignUpUseCase by lazy { SignUpUseCase(authRepository) }

    private val getFavoriteGenresUseCase: GetFavoriteGenresUseCase by lazy {
        GetFavoriteGenresUseCase(
            favoriteGenresRepository
        )
    }
    private val addFavoriteGenreUseCase: AddFavoriteGenreUseCase by lazy {
        AddFavoriteGenreUseCase(
            favoriteGenresRepository
        )
    }
    private val deleteFavoriteGenreUseCase: DeleteFavoriteGenreUseCase by lazy {
        DeleteFavoriteGenreUseCase(
            favoriteGenresRepository
        )
    }

    private val getFriendsUseCase: GetFriendsUseCase by lazy { GetFriendsUseCase(friendsRepository) }
    private val addFriendUseCase: AddFriendUseCase by lazy { AddFriendUseCase(friendsRepository) }
    private val deleteFriendUseCase: DeleteFriendUseCase by lazy {
        DeleteFriendUseCase(
            friendsRepository
        )
    }

    private val getNextUseCase: GetNextUseCase by lazy {
        GetNextUseCase(
            moviesPreferences,
            movieRepository
        )
    }

    private val addReviewUseCase: AddReviewUseCase by lazy { AddReviewUseCase(reviewRepository) }
    private val editReviewUseCase: EditReviewUseCase by lazy { EditReviewUseCase(reviewRepository) }
    private val deleteReviewUseCase: DeleteReviewUseCase by lazy {
        DeleteReviewUseCase(
            reviewRepository
        )
    }

    fun provideMoviesViewModel(): MoviesViewModel {
        return MoviesViewModel(
            getTopMoviesUseCase = getTopMoviesUseCase,
            getMoviesPageUseCase = getMoviesPageUseCase,
            getFavoriteGenresUseCase = getFavoriteGenresUseCase
        )
    }

    fun provideSignUpViewModel(): SignUpViewModel {
        return SignUpViewModel(signUpUseCase = signUpUseCase)
    }

    fun provideSignInViewModel(): SignInViewModel {
        return SignInViewModel(signInUseCase = signInUseCase)
    }

    fun provideMovieDetailsViewModel(): MovieDetailsViewModel {
        return MovieDetailsViewModel(
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            deleteFromFavoritesUseCase = deleteFromFavoritesUseCase,
            getFavoriteGenresUseCase = getFavoriteGenresUseCase,
            addFavoriteGenreUseCase = addFavoriteGenreUseCase,
            deleteFavoriteGenreUseCase = deleteFavoriteGenreUseCase,
            addFriendUseCase = addFriendUseCase,
            getFriendsUseCase = getFriendsUseCase,
            addReviewUseCase = addReviewUseCase,
            editReviewUseCase = editReviewUseCase,
            deleteReviewUseCase = deleteReviewUseCase
        )
    }

    fun provideFavoritesViewModel(): FavoritesViewModel {
        return FavoritesViewModel(
            getFavoritesUseCase = getFavoritesUseCase,
            getFavoriteGenresUseCase = getFavoriteGenresUseCase,
            deleteFavoriteGenreUseCase = deleteFavoriteGenreUseCase
        )
    }

    fun provideFriendsViewModel(): FriendsViewModel {
        return FriendsViewModel(
            getFriendsUseCase = getFriendsUseCase,
            addFriendUseCase = addFriendUseCase,
            deleteFriendUseCase = deleteFriendUseCase
        )
    }

    fun provideFeedViewModel(): FeedViewModel {
        return FeedViewModel(
            getNextUseCase = getNextUseCase,
            addFavoriteUseCase = addToFavoritesUseCase,
            getFavoriteGenresUseCase = getFavoriteGenresUseCase
        )
    }

    fun provideProfileViewModel(): ProfileViewModel {
        return ProfileViewModel(
            getUserProfileUseCase = getUserProfileUseCase,
            logOutUseCase = logOutUseCase
        )
    }

    fun provideLaunchViewModel(): LaunchViewModel {
        return LaunchViewModel(getUserProfileUseCase = getUserProfileUseCase)
    }
}

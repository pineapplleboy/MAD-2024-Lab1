package com.example.moviecatalog.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.model.UserProfile
import com.example.moviecatalog.domain.usecase.profile.GetUserProfileUseCase
import com.example.moviecatalog.domain.usecase.profile.LogOutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logOutUseCase: LogOutUseCase
): ViewModel() {

    private val profileMutable = MutableLiveData<UserProfile?>()
    val profile: LiveData<UserProfile?> get() = profileMutable


    fun getProfile(){
        viewModelScope.launch {
            val result = getUserProfileUseCase.execute()

            result.onSuccess {
                profileMutable.value = it
            }
        }
    }

    fun logOut(){
        viewModelScope.launch{
            val result = logOutUseCase.execute()

            result.onSuccess {
                profileMutable.value = null
            }
        }
    }
}
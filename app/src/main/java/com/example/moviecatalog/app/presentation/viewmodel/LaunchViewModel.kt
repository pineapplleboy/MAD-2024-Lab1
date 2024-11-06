package com.example.moviecatalog.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.model.UserProfile
import com.example.moviecatalog.domain.usecase.profile.GetUserProfileUseCase
import kotlinx.coroutines.launch

class LaunchViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase
): ViewModel() {

    private val resultMutable = MutableLiveData<Int>(0)
    val result: LiveData<Int> get() = resultMutable

    fun getProfile(){
        viewModelScope.launch {
            val profile = getUserProfileUseCase.execute()

            profile.onSuccess {
                resultMutable.value = 1
            }

            profile.onFailure {
                resultMutable.value = 2
            }
        }
    }
}
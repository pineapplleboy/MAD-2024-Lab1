package com.example.moviecatalog.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.usecase.profile.SignUpUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val signUpResultMutable = MutableLiveData(false)
    val signUpResult: LiveData<Boolean> get() = signUpResultMutable

    fun signUp(userRegister: UserRegister) {
        viewModelScope.launch {
            val result = signUpUseCase.execute(userRegister)

            result.onSuccess {
                signUpResultMutable.value = true
            }
            result.onFailure {
                signUpResultMutable.value = false
                Log.d("API", it.toString())
            }
        }
    }
}
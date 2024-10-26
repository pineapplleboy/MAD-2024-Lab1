package com.example.moviecatalog.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.usecase.SignInUseCase
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val signInResultMutable = MutableLiveData(false)
    val signInResult: LiveData<Boolean> get() = signInResultMutable

    fun signIn(loginCredentials: LoginCredentials) {
        viewModelScope.launch {
            val result = signInUseCase.execute(loginCredentials)

            result.onSuccess {
                signInResultMutable.value = true
            }
            result.onFailure {
                signInResultMutable.value = false
                Log.d("API", it.toString())
            }
        }
    }
}
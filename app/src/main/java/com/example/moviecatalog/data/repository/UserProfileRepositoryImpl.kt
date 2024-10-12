package com.example.moviecatalog.data.repository

import android.util.Log
import com.example.moviecatalog.data.api.AuthApi
import com.example.moviecatalog.data.model.ApiToken
import com.example.moviecatalog.data.model.ApiUserRegister
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.repository.UserProfileRepository

class UserProfileRepositoryImpl(
    private val authApi: AuthApi,
    private val authPreferences: AuthPreferences
): UserProfileRepository {

    override fun register(userRegister: UserRegister, callback: ((Result<String>) -> Unit)) {

        authApi.register(
            ApiUserRegister(
                userName = userRegister.login,
                email = userRegister.email,
                name = userRegister.name,
                password = userRegister.password,
                birthDate = userRegister.birthday,
                gender = userRegister.gender
            )
        ).enqueue((object : retrofit2.Callback<ApiToken> {
            override fun onResponse(call: retrofit2.Call<ApiToken>, response: retrofit2.Response<ApiToken>) {
                if (response.isSuccessful) {

                    response.body()?.token?.let { authPreferences.saveToken(it) }
                    callback(Result.success("Registration successful!"))
                    Log.d("API", authPreferences.getToken() ?: "")

                } else {

                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Registration failed: $errorBody")))

                }
            }

            override fun onFailure(call: retrofit2.Call<ApiToken>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }
        }))
    }
}
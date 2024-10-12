package com.example.moviecatalog.data.repository

import android.util.Log
import com.example.moviecatalog.data.api.AuthApi
import com.example.moviecatalog.data.model.ApiLogOutMsg
import com.example.moviecatalog.data.model.ApiLoginCredentials
import com.example.moviecatalog.data.model.ApiToken
import com.example.moviecatalog.data.model.ApiUserRegister
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.repository.UserProfileRepository
import retrofit2.Call
import retrofit2.Response

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

    override fun login(loginCredentials: LoginCredentials, callback: (Result<String>) -> Unit) {

        authApi.login(
            ApiLoginCredentials(
                username = loginCredentials.login,
                password = loginCredentials.password
            )
        ).enqueue(object: retrofit2.Callback<ApiToken> {

            override fun onResponse(call: Call<ApiToken>, response: Response<ApiToken>) {

                if(response.isSuccessful){

                    response.body()?.token?.let {authPreferences.saveToken(it)}
                    callback(Result.success("Login successful!"))
                    Log.d("API", authPreferences.getToken() ?: "")
                }
                else{

                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Login failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<ApiToken>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }
        })
    }

    override fun logout(callback: (Result<String>) -> Unit) {

        authApi.logout().enqueue(object: retrofit2.Callback<ApiLogOutMsg>{

            override fun onResponse(call: Call<ApiLogOutMsg>, response: Response<ApiLogOutMsg>) {
                if(response.isSuccessful){

                    authPreferences.saveToken("")
                    callback(Result.success("Logout successful!"))
                }
                else{

                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Logout failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<ApiLogOutMsg>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }

        })
    }
}
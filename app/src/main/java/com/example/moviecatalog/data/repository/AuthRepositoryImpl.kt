package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.MovieCatalogApi
import com.example.moviecatalog.data.model.ApiLoginCredentials
import com.example.moviecatalog.data.model.ApiUserRegister
import com.example.moviecatalog.data.preferences.AuthPreferences
import com.example.moviecatalog.data.safeApiCall
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.model.UserRegister
import com.example.moviecatalog.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: MovieCatalogApi,
    private val authPreferences: AuthPreferences
) : AuthRepository {

    override suspend fun register(userRegister: UserRegister): Result<Unit> {

        return safeApiCall(
            apiCall = {
                api.register(
                    ApiUserRegister(
                        userName = userRegister.login,
                        email = userRegister.email,
                        name = userRegister.name,
                        password = userRegister.password,
                        birthDate = userRegister.birthday,
                        gender = userRegister.gender
                    )
                ).execute()
            },
            transform = {
                authPreferences.saveToken(it.token)
            }
        )
    }

    override suspend fun login(loginCredentials: LoginCredentials): Result<Unit> {

        return safeApiCall(
            apiCall = {
                api.login(
                    ApiLoginCredentials(
                        username = loginCredentials.login,
                        password = loginCredentials.password
                    )
                ).execute()
            },
            transform = {
                authPreferences.saveToken(it.token)
            }
        )
    }

    override suspend fun logout(): Result<Unit> {

        return safeApiCall(
            apiCall = {
                api.logout().execute()
            },
            transform = {
                authPreferences.saveToken("")
            }
        )
    }
}
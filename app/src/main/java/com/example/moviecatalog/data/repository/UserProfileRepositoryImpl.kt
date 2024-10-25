package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.AuthApi
import com.example.moviecatalog.data.model.ApiUserProfile
import com.example.moviecatalog.data.safeApiCall
import com.example.moviecatalog.domain.model.UserProfile
import com.example.moviecatalog.domain.repository.UserProfileRepository
import retrofit2.Call
import retrofit2.Response

class UserProfileRepositoryImpl(
    private val authApi: AuthApi
): UserProfileRepository {

    override suspend fun getProfile(): Result<UserProfile> {

        return safeApiCall(
            apiCall = {
                authApi.getProfile().execute()
            },
            transform = {
                UserProfile(
                    id = it.id,
                    avatarLink = it.avatarLink,
                    birthday = it.birthDate,
                    email = it.email,
                    gender = it.gender,
                    login = it.name,
                    name = it.name
                )
            }
        )
    }
}
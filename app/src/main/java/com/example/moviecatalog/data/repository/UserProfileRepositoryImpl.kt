package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.MovieCatalogApi
import com.example.moviecatalog.data.safeApiCall
import com.example.moviecatalog.domain.model.UserProfile
import com.example.moviecatalog.domain.repository.UserProfileRepository

class UserProfileRepositoryImpl(
    private val movieCatalogApi: MovieCatalogApi
): UserProfileRepository {

    override suspend fun getProfile(): Result<UserProfile> {

        return safeApiCall(
            apiCall = {
                movieCatalogApi.getProfile().execute()
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
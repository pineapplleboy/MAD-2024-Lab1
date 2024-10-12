package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.api.AuthApi
import com.example.moviecatalog.data.model.ApiUserProfile
import com.example.moviecatalog.domain.model.UserProfile
import com.example.moviecatalog.domain.repository.UserProfileRepository
import retrofit2.Call
import retrofit2.Response

class UserProfileRepositoryImpl(
    private val authApi: AuthApi
): UserProfileRepository {

    override fun getProfile(callback: (Result<UserProfile>) -> Unit) {

        authApi.getProfile().enqueue(object : retrofit2.Callback<ApiUserProfile>{

            override fun onResponse(
                call: Call<ApiUserProfile>,
                response: Response<ApiUserProfile>
            ) {
                if(response.isSuccessful){

                    val profileData = response.body()
                    if(profileData != null){
                        callback(Result.success(
                            UserProfile(
                                id = profileData.id,
                                avatarLink = profileData.avatarLink,
                                birthday = profileData.birthDate,
                                email = profileData.email,
                                gender = profileData.gender,
                                login = profileData.name,
                                name = profileData.name
                            ))
                        )
                    }
                }
                else{
                    val errorBody = response.errorBody()?.string()
                    callback(Result.failure(Exception("Getting profile data failed: $errorBody")))
                }
            }

            override fun onFailure(call: Call<ApiUserProfile>, t: Throwable) {
                callback(Result.failure(Exception("Network error: ${t.message}")))
            }
        })
    }
}
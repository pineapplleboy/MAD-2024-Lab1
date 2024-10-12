package com.example.moviecatalog.data.model

data class ApiUserProfile(
    val id: String,
    val nickName: String?,
    val name: String,
    val email: String,
    val avatarLink: String?,
    val birthDate: String,
    val gender: Int
)
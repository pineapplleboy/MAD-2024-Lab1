package com.example.moviecatalog.data.model

data class ApiUserProfile(
    val id: String,
    val login: String?,
    val name: String,
    val email: String,
    val avatarLink: String?,
    val birthday: String,
    val gender: Int
)
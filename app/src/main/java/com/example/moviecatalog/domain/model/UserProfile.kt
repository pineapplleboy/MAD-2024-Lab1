package com.example.moviecatalog.domain.model

class UserProfile(
    val id: String,
    val login: String?,
    val name: String,
    val email: String,
    val avatarLink: String?,
    val birthday: String,
    val gender: Int
)
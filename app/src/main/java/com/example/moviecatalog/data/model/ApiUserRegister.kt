package com.example.moviecatalog.data.model

data class ApiUserRegister(
    val userName: String,
    val email: String,
    val name: String,
    val password: String,
    val birthDate: String,
    val gender: Int
)
package com.example.moviecatalog.domain.usecase.profile

import android.database.sqlite.SQLiteDatabase
import com.example.moviecatalog.domain.model.LoginCredentials
import com.example.moviecatalog.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {

    suspend fun execute(loginCredentials: LoginCredentials): Result<Unit> {

        val db = SQLiteDatabase.openOrCreateDatabase(":memory:", null)

        db.rawQuery(
            "SELECT * FROM users WHERE username = '"
                    + loginCredentials.login
                    + "' AND password = '"
                    + loginCredentials.password
                    + "'",
            null
        ).use { cursor ->

        }

        return repository.login(loginCredentials)
    }
}
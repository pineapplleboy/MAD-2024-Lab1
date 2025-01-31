package com.example.moviecatalog.domain.usecase.friends

import com.example.moviecatalog.domain.model.UserShort
import com.example.moviecatalog.domain.repository.FriendsRepository

class GetFriendsUseCase(
    private val repository: FriendsRepository
) {
    fun execute(): List<UserShort>{
        return repository.getFriends()
    }
}
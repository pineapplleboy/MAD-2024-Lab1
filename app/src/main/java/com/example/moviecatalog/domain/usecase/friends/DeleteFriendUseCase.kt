package com.example.moviecatalog.domain.usecase.friends

import com.example.moviecatalog.domain.model.UserShort
import com.example.moviecatalog.domain.repository.FriendsRepository

class DeleteFriendUseCase(
    private val repository: FriendsRepository
) {
    fun execute(user: UserShort) {
        return repository.deleteFriend(user)
    }
}
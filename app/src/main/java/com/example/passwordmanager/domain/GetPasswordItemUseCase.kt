package com.example.passwordmanager.domain

class GetPasswordItemUseCase(private val passwordListRepository: PasswordListRepository) {
    suspend fun getPasswordItem(passwordItemId: Int): PasswordItem {
        return passwordListRepository.getPassword(passwordItemId)
    }
}
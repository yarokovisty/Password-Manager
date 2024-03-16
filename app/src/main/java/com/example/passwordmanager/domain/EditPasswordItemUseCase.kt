package com.example.passwordmanager.domain

class EditPasswordItemUseCase(private val passwordListRepository: PasswordListRepository) {
    suspend fun editPasswordItem(passwordItem: PasswordItem) {
        passwordListRepository.editPassword(passwordItem)
    }
}
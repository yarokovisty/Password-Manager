package com.example.passwordmanager.domain

class DeletePasswordItemUseCase(private val passwordListRepository: PasswordListRepository) {
    suspend fun deletePasswordItem(passwordItem: PasswordItem) {
        passwordListRepository.deletePassword(passwordItem)
    }
}
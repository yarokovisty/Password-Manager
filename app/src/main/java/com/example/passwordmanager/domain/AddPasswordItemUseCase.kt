package com.example.passwordmanager.domain

class AddPasswordItemUseCase(private val passwordListRepository: PasswordListRepository) {
        suspend fun addPasswordItem(passwordItem: PasswordItem) {
                passwordListRepository.addPassword(passwordItem)
        }
}
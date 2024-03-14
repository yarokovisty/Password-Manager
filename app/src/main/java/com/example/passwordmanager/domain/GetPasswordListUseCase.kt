package com.example.passwordmanager.domain

import androidx.lifecycle.LiveData

class GetPasswordListUseCase(private val passwordListRepository: PasswordListRepository) {
    fun getPasswordList(): LiveData<List<PasswordItem>> {
        return passwordListRepository.passwordList
    }
}
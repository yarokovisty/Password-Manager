package com.example.passwordmanager.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.passwordmanager.data.room.PasswordDatabase
import com.example.passwordmanager.domain.GetPasswordListUseCase
import com.example.passwordmanager.domain.PasswordListRepository

class PasswordListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PasswordListRepository
    init {
        val passwordItemDao = PasswordDatabase.getDatabase(application).passwordItemDao()
        repository = PasswordListRepository(passwordItemDao)
    }

    private val getPasswordListUseCase = GetPasswordListUseCase(repository)

    val passwordList = getPasswordListUseCase.getPasswordList()

}
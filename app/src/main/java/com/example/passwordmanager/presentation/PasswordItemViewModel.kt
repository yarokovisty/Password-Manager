package com.example.passwordmanager.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.room.PasswordDatabase
import com.example.passwordmanager.domain.AddPasswordItemUseCase
import com.example.passwordmanager.domain.PasswordItem
import com.example.passwordmanager.domain.PasswordListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PasswordListRepository
    init {
        val passwordItemDao = PasswordDatabase.getDatabase(application).passwordItemDao()
        repository = PasswordListRepository(passwordItemDao)
    }

    private val addPasswordItemUseCase = AddPasswordItemUseCase(repository)

    fun addPasswordItem(
        inputName: String,
        inputUrl: String,
        inputLogin: String,
        inputPassword: String
    ) {
        val passwordItem = PasswordItem(
            0,
            inputName,
            inputUrl,
            "",
            inputLogin,
            inputPassword
        )
        viewModelScope.launch(Dispatchers.IO) {
            addPasswordItemUseCase.addPasswordItem(passwordItem)
        }
    }
}
package com.example.passwordmanager.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.room.PasswordDatabase
import com.example.passwordmanager.domain.DeletePasswordItemUseCase
import com.example.passwordmanager.domain.GetPasswordListUseCase
import com.example.passwordmanager.domain.PasswordItem
import com.example.passwordmanager.domain.PasswordListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PasswordListRepository
    val passwordList: LiveData<List<PasswordItem>>
    init {
        val passwordItemDao = PasswordDatabase.getDatabase(application).passwordItemDao()
        repository = PasswordListRepository(passwordItemDao)
        passwordList = repository.passwordList
    }

    private val getPasswordListUseCase = GetPasswordListUseCase(repository)
    private val deletePasswordItemUseCase = DeletePasswordItemUseCase(repository)


    fun deletePasswordItem(passwordItem: PasswordItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePasswordItemUseCase.deletePasswordItem(passwordItem)
        }
    }
}
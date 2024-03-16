package com.example.passwordmanager.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.remote.RemoteRepositoryImpl
import com.example.passwordmanager.data.room.PasswordDatabase
import com.example.passwordmanager.domain.AddPasswordItemUseCase
import com.example.passwordmanager.domain.GetPasswordItemUseCase
import com.example.passwordmanager.domain.PasswordItem
import com.example.passwordmanager.domain.PasswordListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PasswordListRepository
    private val repositoryRemote: RemoteRepositoryImpl

    init {
        val passwordItemDao = PasswordDatabase.getDatabase(application).passwordItemDao()
        repository = PasswordListRepository(passwordItemDao)
        repositoryRemote = RemoteRepositoryImpl()
    }

    private val addPasswordItemUseCase = AddPasswordItemUseCase(repository)
    private val getPasswordItemUseCase = GetPasswordItemUseCase(repository)

    private val _passwordItem = MutableLiveData<PasswordItem>()
    val passwordItem: LiveData<PasswordItem>
        get() = _passwordItem

    private val _urlIcon = MutableLiveData<String>()
    val urlIcon: LiveData<String>
        get() = _urlIcon

    fun addPasswordItem(
        inputName: String,
        inputUrl: String,
        inputLogin: String,
        inputPassword: String,
        inputImg: String = ""
    ) {

        val passwordItem = PasswordItem(
            0,
            inputName,
            inputImg,
            inputUrl,
            inputLogin,
            inputPassword
        )
        viewModelScope.launch(Dispatchers.IO) {
            addPasswordItemUseCase.addPasswordItem(passwordItem)
        }

    }

    fun getPasswordItem(passwordItemId: Int) {
        viewModelScope.launch {
            val item = getPasswordItemUseCase.getPasswordItem(passwordItemId)
            _passwordItem.value = item
        }

    }

    fun getUrlIcon(url: String) {
        repositoryRemote.getUrlIcon(url) {
            _urlIcon.postValue(it)
        }
    }



}
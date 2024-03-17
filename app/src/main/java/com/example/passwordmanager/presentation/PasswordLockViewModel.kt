package com.example.passwordmanager.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.passwordmanager.data.PasswordRepositoryImpl
import com.example.passwordmanager.domain.FirstTimePreference

class PasswordLockViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PasswordRepositoryImpl(application)
    private val firstTimePreference = FirstTimePreference(application)

    private var firstTime: Boolean? = null

    private val _errorInputPass = MutableLiveData<Boolean>()
    val errorInputPass
        get() = _errorInputPass

    private val _errorInputConfirm = MutableLiveData<Boolean>()
    val errorInputConfirm
        get() = _errorInputConfirm

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    init {
        if (firstTimePreference.isFirstTime) {
            firstTime = true
            firstTimePreference.isFirstTime = false
        } else {
            firstTime = false
        }
    }

    fun checkFirstLaunch() = firstTime!!

    fun savePassword(password: String, passwordConfirm: String) {
        if (validPassword(password, passwordConfirm)) {
            repository.savePassword(password)
            finishWork()
        }
    }

    fun getPassword(): String = repository.getPassword() ?: throw RuntimeException()

    private fun validPassword(password: String, passwordConfirm: String): Boolean {
        var result = true

        if (password.isEmpty() || password.length < 4) {
            _errorInputPass.value = true
            result = false
        }
        if (passwordConfirm.isEmpty() || passwordConfirm.length < 4) {
            _errorInputConfirm.value = true
            result = false
        }
        if (password != passwordConfirm) {
            _errorInputConfirm.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputPass() {
        _errorInputPass.value = false
    }

    fun resetErrorInputConfirm() {
        _errorInputConfirm.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}
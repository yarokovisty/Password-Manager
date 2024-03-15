package com.example.passwordmanager.domain

import androidx.lifecycle.LiveData
import com.example.passwordmanager.data.room.PasswordItemDao

class PasswordListRepository(private val passwordItemDao: PasswordItemDao) {
    val passwordList = passwordItemDao.getPasswordList()

    suspend fun addPassword(passwordItem: PasswordItem) {
        passwordItemDao.addPassword(passwordItem)
    }

    suspend fun deletePassword(passwordItem: PasswordItem) {
        passwordItemDao.deletePassword(passwordItem)
    }



}
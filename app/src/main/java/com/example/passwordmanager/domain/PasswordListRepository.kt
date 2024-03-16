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

    suspend fun getPassword(passwordItemId: Int): PasswordItem {
        return passwordItemDao.getPassword(passwordItemId)
    }

    suspend fun editPassword(passwordItem: PasswordItem) {
        passwordItemDao.editPassword(passwordItem)
    }

}
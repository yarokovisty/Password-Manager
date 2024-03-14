package com.example.passwordmanager.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.passwordmanager.domain.PasswordItem

@Dao
interface PasswordItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPassword(passwordItem: PasswordItem)

    @Query("SELECT * FROM password_table ORDER BY id ASC")
    fun getPasswordList(): LiveData<List<PasswordItem>>
}
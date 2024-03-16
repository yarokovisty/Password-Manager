package com.example.passwordmanager.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.passwordmanager.domain.PasswordItem

@Dao
interface PasswordItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPassword(passwordItem: PasswordItem)

    @Delete
    suspend fun deletePassword(passwordItem: PasswordItem)

    @Update
    suspend fun editPassword(passwordItem: PasswordItem)

    @Query("SELECT * FROM password_table ORDER BY id ASC")
    fun getPasswordList(): LiveData<List<PasswordItem>>

    @Query("SELECT * FROM password_table WHERE id = :itemId")
    suspend fun getPassword(itemId: Int): PasswordItem
}
package com.example.passwordmanager.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_table")
data class PasswordItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nameSite: String,
    val imgSite: String,
    val urlSite: String,
    val login: String,
    val password: String
)

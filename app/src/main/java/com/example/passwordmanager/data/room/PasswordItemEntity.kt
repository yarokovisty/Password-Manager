package com.example.passwordmanager.data.room


data class PasswordItemEntity(
    val id: Int,
    val nameSite: String,
    val imgSite: String,
    val urlSite: String,
    val login: String,
    val password: String
)
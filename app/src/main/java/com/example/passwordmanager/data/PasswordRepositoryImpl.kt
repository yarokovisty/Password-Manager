package com.example.passwordmanager.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.crypto.EncryptedPrivateKeyInfo

class PasswordRepositoryImpl(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        SHARED_PREF,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun savePassword(password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY, password)
        editor.apply()
    }

    fun getPassword() = sharedPreferences.getString(KEY, null)

    companion object {
        private const val SHARED_PREF = "secret_shared_prefs"
        private const val KEY = "manager_password"
    }
}
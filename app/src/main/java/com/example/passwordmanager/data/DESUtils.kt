package com.example.passwordmanager.data

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.SecretKeySpec

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("GetInstance")
object DESUtils {
    // алгоритм DES через библиотеки
    private const val ALGORITHM = "DES"
    private const val KEY = "password"

    fun encrypt(text: String, key: String = KEY): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(key))
        val encryptedBytes = cipher.doFinal(text.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encryptedText: String, key: String = KEY): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, generateSecretKey(key))
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText))
        return String(decryptedBytes)
    }

    private fun generateSecretKey(key: String = KEY): SecretKeySpec {
        val desKey = DESKeySpec(key.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val secretKey = keyFactory.generateSecret(desKey)
        return SecretKeySpec(secretKey.encoded, ALGORITHM)
    }
}
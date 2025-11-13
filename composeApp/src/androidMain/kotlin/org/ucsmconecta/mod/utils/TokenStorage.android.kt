package org.ucsmconecta.mod.utils

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.ucsmconecta.mod.MyApplication

class TokenStorageAndroid(context: Context): TokenStorage {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveToken(token: String, role: String, id: Long) {
        val now = System.currentTimeMillis()
        prefs.edit {
            putString("jwt_token", token)
                .putString("role", role)
                .putLong("id", id)
                .putLong("token_timestamp", now)
        }
    }
    override suspend fun getToken(): String? {
        val token = prefs.getString("jwt_token", null)
        val timestamp = prefs.getLong("token_timestamp", 0L)
        if (token != null && System.currentTimeMillis() - timestamp > 86400000L) {
            // Más de 24 horas -> borrar token
            clear()
            return null
        }
        return token
    }
    override suspend fun getRole(): String? = prefs.getString("role", null)
    override suspend fun getId(): Long = prefs.getLong("id", 0L)
    override suspend fun clear() {
        prefs.edit { clear() }
    }
}
actual fun getTokenStorage(): TokenStorage {
    // Usa un contexto global
    val appContext = MyApplication.instance.applicationContext
    return TokenStorageAndroid(appContext)
}
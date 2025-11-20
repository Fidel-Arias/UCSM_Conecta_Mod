package org.ucsmconecta.mod.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.ucsmconecta.mod.MyApplication
import java.io.IOException
import java.security.GeneralSecurityException

class TokenStorageAndroid(context: Context): TokenStorage {
    private val PREFS_FILE_NAME = "auth_prefs"
    private val applicationContext = context.applicationContext

    // Propiedad perezosa y nullable para manejar el estado de error
    private val prefs: SharedPreferences? by lazy {
        // Intentamos inicializar las preferencias seguras
        initializeEncryptedPrefs()
    }

    private fun initializeEncryptedPrefs(): SharedPreferences? {
        try {
            // 1. Crear la Master Key
            val masterKey = MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            // 2. Intentar crear/cargar las preferencias (Riesgo de AEADBadTagException)
            return EncryptedSharedPreferences.create(
                applicationContext,
                PREFS_FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

        } catch (e: GeneralSecurityException) {
            // Captura: AEADBadTagException, KeyStoreException, etc.
            // La clave o el archivo están corruptos.
            Log.e("TokenStorage", "Error de seguridad/descifrado: ${e.message}. Forzando limpieza.", e)

            cleanupCorruptedPrefs()

            // Reintenta la inicialización después de la limpieza
            return initializeEncryptedPrefsRetry()

        } catch (e: IOException) {
            // Captura errores de I/O
            Log.e("TokenStorage", "Error de I/O al cargar las preferencias: ${e.message}.", e)
            cleanupCorruptedPrefs()
            return initializeEncryptedPrefsRetry()
        }
    }

    private fun cleanupCorruptedPrefs() {
        // Elimina el archivo de preferencias corrupto
        val success = applicationContext.deleteSharedPreferences(PREFS_FILE_NAME)
        Log.i("TokenStorage", "Archivo de preferencias limpiado: $success")
    }

    private fun initializeEncryptedPrefsRetry(): SharedPreferences? {
        // Segundo intento: el archivo debería estar limpio ahora.
        return try {
            val masterKey = MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            EncryptedSharedPreferences.create(
                applicationContext,
                PREFS_FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            // Si incluso el reintento falla, la seguridad es inestable.
            Log.e("TokenStorage", "Fallo definitivo tras la limpieza: ${e.message}", e)
            null // Indica que el almacenamiento no está disponible
        }
    }

    // --- Métodos de la Interfaz TokenStorage ---
    override suspend fun saveToken(token: String, role: String, id: Long) {
        prefs?.edit {
            val now = System.currentTimeMillis()
            putString("jwt_token", token)
                .putString("role", role)
                .putLong("id", id)
                .putLong("token_timestamp", now)
        } ?: Log.w("TokenStorage", "No se pudo guardar el token. Preferencias no inicializadas.")
    }
    override suspend fun getToken(): String? {
        // Si prefs es nulo, retornar null inmediatamente
        val prefsInstance = prefs ?: return null

        val token = prefsInstance.getString("jwt_token", null)
        val timestamp = prefsInstance.getLong("token_timestamp", 0L)

        if (token != null && System.currentTimeMillis() - timestamp > 86400000L) {
            // Más de 24 horas -> borrar token
            clear()
            return null
        }
        return token
    }
    override suspend fun getRole(): String? = prefs?.getString("role", null)
    override suspend fun getId(): Long = prefs?.getLong("id", 0L) ?: 0L
    override suspend fun clear() {
        prefs?.edit { clear() }
    }
}
actual fun getTokenStorage(): TokenStorage {
    // Usa un contexto global
    val appContext = MyApplication.instance.applicationContext
    return TokenStorageAndroid(appContext)
}
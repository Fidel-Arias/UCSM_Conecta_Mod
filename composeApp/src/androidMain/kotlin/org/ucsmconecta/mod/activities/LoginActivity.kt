package org.ucsmconecta.mod.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.ucsmconecta.mod.interfaceApp.LoginApp

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Ocultar la barra de estado y navegación
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Crear la master key
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // Usar las mismas preferencias seguras
        val prefs = EncryptedSharedPreferences.create(
            this,
            "auth_prefs", // mismo nombre que en TokenStorageAndroid
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val token = prefs.getString("jwt_token", null)

        if (token != null) {
            startActivityAsistant()
            finish()
            return
        }

        setContent {
            LoginApp { startActivityAsistant() }
        }
    }
    private fun startActivityAsistant() {
        startActivity(Intent(this, AsistantActivity::class.java))
    }
}
package org.ucsmconecta.mod.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.interfaceApp.LoginApp
import org.ucsmconecta.mod.utils.TokenStorage
import org.ucsmconecta.mod.utils.getTokenStorage

class LoginActivity : ComponentActivity() {
    // Nombre del archivo de preferencias
    private val tokenStorage: TokenStorage = getTokenStorage()

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

        lifecycleScope.launch {
            // getToken() ya se encarga de:
            // 1. Inicializar EncryptedSharedPreferences (con MasterKey y manejo de errores).
            // 2. Comprobar si hay un token válido.
            val token = tokenStorage.getToken()

            if (token != null) {
                startActivityAsistant()
                finish()
                return@launch
            }

            setContent {
                LoginApp { startActivityAsistant() }
            }
        }
    }

    private fun startActivityAsistant() {
        startActivity(Intent(this, AsistantActivity::class.java))
    }
}
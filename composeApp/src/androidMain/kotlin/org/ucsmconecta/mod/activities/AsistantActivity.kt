package org.ucsmconecta.mod.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.ucsmconecta.mod.interfaceApp.AsistantApp
import org.ucsmconecta.mod.service.network.initNetworkMonitor
import org.ucsmconecta.mod.service.settings.initSettingsApp
import org.ucsmconecta.mod.ui.theme.PrimaryColor

class AsistantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initNetworkMonitor(applicationContext) // Se inicializa el contexto de red
        initSettingsApp(applicationContext) // Se inicializa el contexto de configuración

        // Ocultar la barra de navegación
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Cambiar el color de la barra de estado a cyan
        window.statusBarColor = PrimaryColor.toArgb() // cyan

        setContent {
            AsistantApp("Escuela Profesional de Ingeniería de Sistemas")
        }
    }
}
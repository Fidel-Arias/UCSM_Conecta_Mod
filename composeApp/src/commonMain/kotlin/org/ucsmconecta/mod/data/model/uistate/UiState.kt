package org.ucsmconecta.mod.data.model.uistate

// Estado genérico para cualquier pantalla
sealed class UiState<out T> {
    object Idle : UiState<Nothing>()        // Estado inicial (sin acción)
    object Loading : UiState<Nothing>()     // Cuando se está procesando algo
    data class Success<out T>(val data: T) : UiState<T>()  // Resultado exitoso
    data class Error(val message: String) : UiState<Nothing>()  // Error con mensaje
}
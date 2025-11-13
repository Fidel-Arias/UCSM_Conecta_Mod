package org.ucsmconecta.mod.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.data.repository.ColaboradorRepository

class RefrigerioViewModel (
    private val repository: ColaboradorRepository
) : ViewModel() {

    private val _mensajeRefrigerio = MutableStateFlow<String?>(null)
    val mensajeRefrigerio = _mensajeRefrigerio.asStateFlow()

    private val _tipoMensaje = MutableStateFlow<Boolean?>(null)
    val tipoMensaje = _tipoMensaje.asStateFlow()

    fun registrarAsistencia(documentoParticipante: String, bloqueId: Long, congresoCod: String) {
        viewModelScope.launch {
            val result = repository.registrarAsistencia(documentoParticipante, bloqueId, congresoCod)

            result
                .onSuccess { response ->
                    // Si el backend devolvió "success", es verde. Si devolvió "error", es rojo.
                    if (response.error != null) {
                        _mensajeRefrigerio.value = response.error
                        _tipoMensaje.value = false // rojo
                    } else {
                        _mensajeRefrigerio.value = response.success ?: "Operación exitosa"
                        _tipoMensaje.value = true // verde
                    }
                }
                .onFailure { error ->
                    _mensajeRefrigerio.value = error.message ?: "Error desconocido"
                    _tipoMensaje.value = false
                }
        }
    }

    fun clearMensaje() {
        _mensajeRefrigerio.value = null
        _tipoMensaje.value = null
    }
}
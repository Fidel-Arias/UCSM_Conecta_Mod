package org.ucsmconecta.mod.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.data.repository.ColaboradorRepository

class ScannerViewModel (
    private val repository: ColaboradorRepository
) : ViewModel() {

    private val _mensajeAsistencia = MutableStateFlow<String?>(null)
    val mensajeAsistencia = _mensajeAsistencia.asStateFlow()

    private val _tipoMensaje = MutableStateFlow<Boolean?>(null)
    val tipoMensaje = _tipoMensaje.asStateFlow()

    fun registrarAsistencia(documentoParticipante: String, bloqueId: Long, congresoCod: String) {
        viewModelScope.launch {
            val result = repository.registrarAsistencia(documentoParticipante, bloqueId, congresoCod)

            result
                .onSuccess { response ->
                    // Si el backend devolvió "success", es verde. Si devolvió "error", es rojo.
                    if (response.error != null) {
                        _mensajeAsistencia.value = response.error
                        _tipoMensaje.value = false // rojo
                    } else {
                        _mensajeAsistencia.value = response.success ?: "Operación exitosa"
                        _tipoMensaje.value = true // verde
                    }
                }
                .onFailure { error ->
                    _mensajeAsistencia.value = error.message ?: "Error desconocido"
                    _tipoMensaje.value = false
                }
        }
    }

    fun registrarRefrigerio(documentoParticipante: String, congresoCod: String) {
        viewModelScope.launch {
            val result = repository.registrarRefrigerio(documentoParticipante, congresoCod)

            result
                .onSuccess { response ->
                    if (response.error != null) {
                        _mensajeAsistencia.value = response.error
                        _tipoMensaje.value = false
                    } else {
                        _mensajeAsistencia.value = response.success ?: "Operación exitosa"
                        _tipoMensaje.value = true
                    }
                }
                .onFailure { error ->
                    _mensajeAsistencia.value = error.message ?: "Error desconocido"
                    _tipoMensaje.value = false
                }
        }
    }

    fun clearMensaje() {
        _mensajeAsistencia.value = null
        _tipoMensaje.value = null
    }

    fun mostrarErrorQR() {
        _mensajeAsistencia.value = "QR inválido"
        _tipoMensaje.value = false
    }

}
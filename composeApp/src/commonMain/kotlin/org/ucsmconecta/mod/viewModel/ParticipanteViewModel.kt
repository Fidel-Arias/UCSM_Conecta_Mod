package org.ucsmconecta.mod.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.data.repository.ColaboradorRepository

class ParticipanteViewModel(
    private val repository: ColaboradorRepository
): ViewModel() {
    private val _mensajeRegistro = MutableStateFlow<String?>(null)
    val mensajeRegistro = _mensajeRegistro.asStateFlow()

    private val _tipoMensaje = MutableStateFlow<Boolean?>(null)
    val tipoMensaje = _tipoMensaje.asStateFlow()

    fun registrarParticipante(nombres: String, apPaterno: String, apMaterno: String, numDocumento: String, email: String, tipoParticipanteId: Long, congresoCodigo: String, escuelaCodigo: String) {
        viewModelScope.launch {
            val result = repository.registrarParticipante(nombres, apPaterno, apMaterno,  numDocumento, email, tipoParticipanteId, congresoCodigo, escuelaCodigo)

            result
                .onSuccess { response ->
                    if (response.error != null) {
                        _mensajeRegistro.value = response.error
                        _tipoMensaje.value = false
                    } else {
                        _mensajeRegistro.value = response.success ?: "Registro exitoso"
                        _tipoMensaje.value = true
                    }
                }
                .onFailure { error ->
                    _mensajeRegistro.value = error.message ?: "Error desconocido"
                    _tipoMensaje.value = false
                }
        }
    }
    fun clearMensaje() {
        _mensajeRegistro.value = null
        _tipoMensaje.value = null
    }
}
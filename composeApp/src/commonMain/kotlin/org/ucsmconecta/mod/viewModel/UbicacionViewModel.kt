package org.ucsmconecta.mod.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.data.model.ubicacion.DataResponseUbicacion
import org.ucsmconecta.mod.data.model.uistate.UiState
import org.ucsmconecta.mod.data.repository.ColaboradorRepository

class UbicacionViewModel(
    private val repository: ColaboradorRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<DataResponseUbicacion>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<DataResponseUbicacion>>> = _uiState

    fun cargarTodasLasUbicaciones() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.obtenerTodasLasUbicaciones()

            result.fold(
                onSuccess = { response ->
                    if (response.isEmpty()) {
                        _uiState.value = UiState.Error("No hay ubicaciones disponibles")
                    } else {
                        _uiState.value = UiState.Success(response)
                    }
                },
                onFailure = { e ->
                    _uiState.value = UiState.Error(e.message ?: "Error desconocido")
                }
            )
        }
    }
}
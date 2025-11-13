package org.ucsmconecta.mod.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.data.model.bloques.DataResponseBloque
import org.ucsmconecta.mod.data.model.uistate.UiState
import org.ucsmconecta.mod.data.repository.ColaboradorRepository

class BloqueViewModel(
    private val repository: ColaboradorRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<DataResponseBloque>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<DataResponseBloque>>> = _uiState

    fun cargarTodosLosBloquesPorDia() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.obtenerTodosLosBloquesPorDiayHora()

            result.fold(
                onSuccess = { response ->
                    if (response.isEmpty()) {
                        _uiState.value = UiState.Error("No hay bloques disponibles")
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
package org.ucsmconecta.mod.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.data.model.colaborador.DataResposeColaboradorCongreso
import org.ucsmconecta.mod.data.model.uistate.UiState
import org.ucsmconecta.mod.data.repository.ColaboradorRepository

class ColaboradorCongresoViewModel(
    private val repository: ColaboradorRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<DataResposeColaboradorCongreso>>(UiState.Idle)
    val uiState: StateFlow<UiState<DataResposeColaboradorCongreso>> = _uiState

    fun cargarDatosColaborador(id: Long) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.obtenerDatosColaboradorCongreso(id)

            result.fold(
                onSuccess = { response ->
                    _uiState.value = UiState.Success(response)
                },
                onFailure = { e ->
                    _uiState.value = UiState.Error(e.message ?: "Error desconocido")
                }
            )
        }
    }
}
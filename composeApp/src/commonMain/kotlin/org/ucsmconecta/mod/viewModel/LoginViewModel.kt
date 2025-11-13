package org.ucsmconecta.mod.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.data.model.login.LoginResponse
import org.ucsmconecta.mod.data.model.uistate.UiState
import org.ucsmconecta.mod.data.repository.AuthRepository

class LoginViewModel(
    private val repository: AuthRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val uiState: StateFlow<UiState<LoginResponse>> = _uiState

    fun login(email: String, password: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val result = repository.login(email, password)

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

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}
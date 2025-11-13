package org.ucsmconecta.mod.data.repository

import org.ucsmconecta.mod.data.model.login.LoginRequest
import org.ucsmconecta.mod.data.model.login.LoginResponse
import org.ucsmconecta.mod.data.network.ApiService
import org.ucsmconecta.mod.utils.TokenStorage

class AuthRepository(
    private val api: ApiService,
) {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return api.login(LoginRequest(email, password))
    }
}
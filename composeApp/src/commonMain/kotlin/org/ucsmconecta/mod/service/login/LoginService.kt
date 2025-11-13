package org.ucsmconecta.mod.service.login

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.ucsmconecta.mod.data.errors.ErrorResponse
import org.ucsmconecta.mod.data.model.login.LoginRequest
import org.ucsmconecta.mod.data.model.login.LoginResponse

class LoginService(
    private val client: HttpClient
) {
    private val BASE_URL = "http://vps-5440778-x.dattaweb.com:8080"
    suspend fun fetchLoginPost(request: LoginRequest): Result<LoginResponse> {
        return try {
            val responseText = client.post("$BASE_URL/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.bodyAsText()

            println("🔥 Respuesta del servidor: $responseText")

            // Intentar parsear como LoginResponse
            val login = Json.decodeFromString<LoginResponse>(responseText)
            Result.success(login)
        } catch (e: SerializationException) {
            // Si falla, intentar parsear como ErrorResponse
            try {
                val error = Json.decodeFromString<ErrorResponse>(e.message ?: "")
                Result.failure(Exception(error.error))
            } catch (_: Exception) {
                Result.failure(Exception("Error desconocido al procesar la respuesta"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
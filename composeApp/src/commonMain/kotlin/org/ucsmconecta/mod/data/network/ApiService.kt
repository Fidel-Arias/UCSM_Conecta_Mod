package org.ucsmconecta.mod.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import okio.IOException
import org.ucsmconecta.mod.data.model.login.LoginRequest
import org.ucsmconecta.mod.data.model.login.LoginResponse
import org.ucsmconecta.mod.data.errors.ErrorResponse
import org.ucsmconecta.mod.data.model.asistencia.DataRequestAsistencia
import org.ucsmconecta.mod.data.model.asistencia.DataResponseAsistencia
import org.ucsmconecta.mod.data.model.bloques.DataResponseBloque
import org.ucsmconecta.mod.data.model.colaborador.DataResposeColaboradorCongreso
import org.ucsmconecta.mod.data.model.participante.DataRequestParticipante
import org.ucsmconecta.mod.data.model.participante.DataResponseParticipante
import org.ucsmconecta.mod.data.model.participante.DataTipoParticipanteResponse
import org.ucsmconecta.mod.data.model.refrigerio.DataRequestRefrigerio
import org.ucsmconecta.mod.data.model.refrigerio.DataResponseRefrigerio
import org.ucsmconecta.mod.data.model.ubicacion.DataResponseUbicacion
import org.ucsmconecta.mod.infra.errors.ConnectionError
import org.ucsmconecta.mod.infra.errors.UnauthorizedException
import org.ucsmconecta.mod.utils.TokenStorage

class ApiService(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage?
) {
    private val BASE_URL = "http://vps-5440778-x.dattaweb.com:8080"

    private suspend fun llamarToken() = tokenStorage?.getToken() ?: throw IllegalStateException("Token no encontrado")

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response: HttpResponse = client.post("$BASE_URL/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            val bodyText = response.bodyAsText()
            val json = Json { ignoreUnknownKeys = true }

            if (response.status.isSuccess()) {
                // Intentar parsear como LoginResponse
                val loginResponse = json.decodeFromString<LoginResponse>(bodyText)
                Result.success(loginResponse)
            } else {
                // Si no es éxito, intentar leer mensaje de error
                val errorResponse = try {
                    json.decodeFromString<ErrorResponse>(bodyText)
                } catch (_: Exception) {
                    ErrorResponse("Error desconocido: ${response.status}")
                }
                Result.failure(Exception(errorResponse.error))
            }

        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    suspend fun obtenerDatosColaborador(idColaborador: Long): Result<DataResposeColaboradorCongreso> {
        return try {
            val token = llamarToken()

            val response = client.get("$BASE_URL/api/colaborador/$idColaborador") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            return when(response.status) {
                HttpStatusCode.Forbidden -> {
                    Result.failure(UnauthorizedException("Token expirado o no autorizado"))
                }
                else -> {
                    val body = response.body<DataResposeColaboradorCongreso>()
                    Result.success(body)
                }
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    suspend fun obtenerTodasLasUbicaciones(): Result<List<DataResponseUbicacion>> {
        return try {
            val token = llamarToken()

            val response = client.get("$BASE_URL/api/colaborador/ubicaciones") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
            return when (response.status) {
                HttpStatusCode.Forbidden -> {
                    Result.failure(UnauthorizedException("Token expirado o no autorizado"))
                }
                HttpStatusCode.NoContent -> {
                    // Manejar respuesta vacía (204)
                    Result.success(emptyList())
                }
                else -> {
                    // Si hay contenido, parsear normalmente
                    val body = response.body<List<DataResponseUbicacion>>()
                    Result.success(body)
                }
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    suspend fun obtenerTodosLosBloquesPorDia(): Result<List<DataResponseBloque>> {
        return try {
            val token = llamarToken()

            val response = client.get("$BASE_URL/api/colaborador/bloques") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            return when (response.status) {
                HttpStatusCode.Forbidden -> {
                    Result.failure(UnauthorizedException("Token expirado o no autorizado"))
                }
                HttpStatusCode.NoContent -> {
                    // Manejar respuesta vacía (204)
                    Result.success(emptyList())
                }
                else -> {
                    // Si hay contenido, parsear normalmente
                    val body = response.body<List<DataResponseBloque>>()
                    Result.success(body)
                }
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    suspend fun obtenerTodosLosTiposParticipantes(): Result<List<DataTipoParticipanteResponse>> {
        return try {
            val token = llamarToken()

            val response = client.get("$BASE_URL/api/tipos-participantes") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
            return when (response.status) {
                HttpStatusCode.Forbidden -> {
                    Result.failure(UnauthorizedException("Token expirado o no autorizado"))
                }
                HttpStatusCode.NoContent -> {
                    // Manejar respuesta vacía (204)
                    Result.success(emptyList())
                }
                else -> {
                    // Si hay contenido, parsear normalmente
                    val body = response.body<List<DataTipoParticipanteResponse>>()
                    Result.success(body)
                }
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    // Registrar asistencia por QR
    suspend fun registrarAsistencia(request: DataRequestAsistencia): Result<DataResponseAsistencia> {
        return try {
            val token = llamarToken()

            val response: HttpResponse = client.post("$BASE_URL/api/colaborador/registrar-asistencia") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }

            val bodyText = response.bodyAsText()
            val json = Json { ignoreUnknownKeys = true }

            if (response.status == HttpStatusCode.Forbidden) {
                return Result.failure(UnauthorizedException("Token expirado o no autorizado"))
            }

            if (response.status.isSuccess()) {
                val parsed = json.decodeFromString<DataResponseAsistencia>(bodyText)
                Result.success(parsed)
            } else {
                val errorResponse = try {
                    json.decodeFromString<ErrorResponse>(bodyText)
                } catch (_: Exception) {
                    ErrorResponse("Error desconocido: ${response.status}")
                }
                Result.failure(Exception(errorResponse.error))
            }

        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    suspend fun registrarRefrigerio(request: DataRequestRefrigerio): Result<DataResponseRefrigerio> {
        return try {
            val token = llamarToken()

            val response: HttpResponse = client.post("$BASE_URL/api/colaborador/registrar-refrigerio") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }

            val bodyText = response.bodyAsText()
            val json = Json { ignoreUnknownKeys = true }

            if (response.status == HttpStatusCode.Forbidden) {
                return Result.failure(UnauthorizedException("Token expirado o no autorizado"))
            }
            if (response.status.isSuccess()) {
                val parsed = json.decodeFromString<DataResponseRefrigerio>(bodyText)
                Result.success(parsed)
            } else {
                val errorResponse = try {
                    json.decodeFromString<ErrorResponse>(bodyText)
                } catch (_: Exception) {
                    ErrorResponse("Error desconocido: ${response.status}")
                }
                Result.failure(Exception(errorResponse.error))
            }

        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    suspend fun registratParticipante(request: DataRequestParticipante, escuelaCod: String, congresoCod: String): Result<DataResponseParticipante> {
        return try {
            val token = llamarToken()

            val response: HttpResponse = client.post("$BASE_URL/api/colaborador/registrar-participante?escuelaCod=$escuelaCod&congresoCod=$congresoCod") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                setBody(request)
            }

            val bodyText = response.bodyAsText()
            println("RESPONSE: $bodyText")
            val json = Json { ignoreUnknownKeys = true }

            if (response.status == HttpStatusCode.Forbidden) {
                return Result.failure(UnauthorizedException("Token expirado o no autorizado"))
            }
            if (response.status.isSuccess()) {
                val parsed = json.decodeFromString<DataResponseParticipante>(bodyText)
                Result.success(parsed)
            } else {
                val errorResponse = try {
                    json.decodeFromString<ErrorResponse>(bodyText)
                } catch (_: Exception) {
                    ErrorResponse("Error desconocido: ${response.status}")
                }
                println("ERROR SERVIDOR: ${errorResponse.error}")
                Result.failure(Exception(errorResponse.error))
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }
}
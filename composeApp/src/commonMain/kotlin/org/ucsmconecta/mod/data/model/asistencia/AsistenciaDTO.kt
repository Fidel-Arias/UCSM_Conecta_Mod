package org.ucsmconecta.mod.data.model.asistencia

import kotlinx.serialization.Serializable

@Serializable
data class DataRequestAsistencia(
    val documentoParticipante: String,
    val bloqueId: Long,
    val congresoCod: String
)

@Serializable
data class DataResponseAsistencia(
    val success: String? = null,
    val error: String? = null
)
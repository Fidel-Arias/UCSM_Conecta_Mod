package org.ucsmconecta.mod.data.model.participante

import kotlinx.serialization.Serializable

@Serializable
data class DataRequestParticipante(
    val nombres: String,
    val apPaterno: String,
    val apMaterno: String,
    val numDocumento: String,
    val email: String,
    val tipoParticipanteId: Long
)

@Serializable
data class DataResponseParticipante(
    val success: String? = null,
    val error: String? = null
)

package org.ucsmconecta.mod.data.model.refrigerio

import kotlinx.serialization.Serializable

@Serializable
data class DataRequestRefrigerio(
    val documentoParticipante: String,
    val congresoCod: String
)

@Serializable
data class DataResponseRefrigerio(
    val success: String? = null,
    val error: String? = null
)
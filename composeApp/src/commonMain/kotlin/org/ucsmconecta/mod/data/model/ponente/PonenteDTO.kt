package org.ucsmconecta.mod.data.model.ponente

import kotlinx.serialization.Serializable

@Serializable
data class DataResponsePonente(
    val id: Long,
    val nombres: String,
    val apellidos: String
)

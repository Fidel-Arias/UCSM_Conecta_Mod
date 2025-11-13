package org.ucsmconecta.mod.data.model.ponencia

import kotlinx.serialization.Serializable
import org.ucsmconecta.mod.data.model.ponente.DataResponsePonente

@Serializable
data class DataResponsePonencia(
    val id: Long,
    val nombre: String,
    val ponente: DataResponsePonente
)
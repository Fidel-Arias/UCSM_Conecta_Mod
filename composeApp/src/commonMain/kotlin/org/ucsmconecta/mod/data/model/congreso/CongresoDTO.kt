package org.ucsmconecta.mod.data.model.congreso

import kotlinx.serialization.Serializable

@Serializable
data class DataCongresoResponse(
    val id: Long,
    val nombre: String,
    val codigo: String
)
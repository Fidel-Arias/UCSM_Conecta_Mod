package org.ucsmconecta.mod.data.model.ubicacion

import kotlinx.serialization.Serializable

@Serializable
data class DataResponseUbicacion(
    val id: Long,
    val nombre: String,
)
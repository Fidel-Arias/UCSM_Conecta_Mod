package org.ucsmconecta.mod.data.model.participante

import kotlinx.serialization.Serializable

@Serializable
data class DataResponseEscuelaProfesional(
    val id: Long,
    val nombre: String,
    val codigo: String
)
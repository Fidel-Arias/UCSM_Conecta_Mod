package org.ucsmconecta.mod.data.model.colaborador

import kotlinx.serialization.Serializable
import org.ucsmconecta.mod.data.model.participante.DataResponseEscuelaProfesional

@Serializable
data class DataResponseColaborador(
    val id: Int,
    val nombres: String,
    val escuelaProfesional: DataResponseEscuelaProfesional
)
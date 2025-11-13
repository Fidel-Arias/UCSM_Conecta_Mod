package org.ucsmconecta.mod.data.model.participante

import kotlinx.serialization.Serializable

@Serializable
data class DataTipoParticipanteResponse(
    val id: Long,
    val descripcion: String
)

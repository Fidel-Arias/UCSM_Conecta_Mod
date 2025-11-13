package org.ucsmconecta.mod.data.model.colaborador

import kotlinx.serialization.Serializable
import org.ucsmconecta.mod.data.model.congreso.DataCongresoResponse

@Serializable
data class DataResposeColaboradorCongreso(
    val colaborador: DataResponseColaborador,
    val congreso: DataCongresoResponse
)
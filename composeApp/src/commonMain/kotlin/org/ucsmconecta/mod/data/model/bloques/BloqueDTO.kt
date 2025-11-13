package org.ucsmconecta.mod.data.model.bloques

import kotlinx.serialization.Serializable
import org.ucsmconecta.mod.data.model.dia.DataResponseDia
import org.ucsmconecta.mod.data.model.ponencia.DataResponsePonencia
import org.ucsmconecta.mod.data.model.ubicacion.DataResponseUbicacion

@Serializable
data class DataResponseBloque(
    val id: Long,
    val horaInicial: String,
    val horaFinal: String,
    val dia: DataResponseDia,
    val ubicacion: DataResponseUbicacion,
    val ponencia: DataResponsePonencia
)

package org.ucsmconecta.mod.data.repository

import org.ucsmconecta.mod.data.model.asistencia.DataRequestAsistencia
import org.ucsmconecta.mod.data.model.asistencia.DataResponseAsistencia
import org.ucsmconecta.mod.data.model.bloques.DataResponseBloque
import org.ucsmconecta.mod.data.model.colaborador.DataResposeColaboradorCongreso
import org.ucsmconecta.mod.data.model.participante.DataRequestParticipante
import org.ucsmconecta.mod.data.model.participante.DataResponseParticipante
import org.ucsmconecta.mod.data.model.participante.DataTipoParticipanteResponse
import org.ucsmconecta.mod.data.model.refrigerio.DataRequestRefrigerio
import org.ucsmconecta.mod.data.model.refrigerio.DataResponseRefrigerio
import org.ucsmconecta.mod.data.model.ubicacion.DataResponseUbicacion
import org.ucsmconecta.mod.data.network.ApiService

class ColaboradorRepository(
    private val api: ApiService
) {
    suspend fun obtenerDatosColaboradorCongreso(idColaborador: Long): Result<DataResposeColaboradorCongreso> {
        return api.obtenerDatosColaborador(idColaborador)
    }

    suspend fun obtenerTodasLasUbicaciones(): Result<List<DataResponseUbicacion>> {
        return api.obtenerTodasLasUbicaciones()
    }

    suspend fun obtenerTodosLosBloquesPorDiayHora(): Result<List<DataResponseBloque>> {
        return api.obtenerTodosLosBloquesPorDia()
    }

    suspend fun obtenerTodosLosTiposParticipantes(): Result<List<DataTipoParticipanteResponse>> {
        return api.obtenerTodosLosTiposParticipantes()
    }

    suspend fun registrarAsistencia(documentoParticipante: String, bloqueId: Long, congresoCod: String): Result<DataResponseAsistencia> {
        return api.registrarAsistencia(DataRequestAsistencia(
            documentoParticipante,
            bloqueId,
            congresoCod)
        )
    }

    suspend fun registrarRefrigerio(documentoParticipante: String, congresoCod: String): Result<DataResponseRefrigerio> {
        return api.registrarRefrigerio(DataRequestRefrigerio(
            documentoParticipante,
            congresoCod
        ))
    }

    suspend fun registrarParticipante(nombres: String, apPaterno: String, apMaterno: String, numDocumento: String, email: String, tipoParticipanteId: Long, congresoCodigo: String, escuelaCodigo: String): Result<DataResponseParticipante> {
        return api.registratParticipante(
            request = DataRequestParticipante(
            nombres,
            apPaterno,
            apMaterno,
            numDocumento,
            email,
            tipoParticipanteId,
                ),
            escuelaCod = escuelaCodigo,
            congresoCod = congresoCodigo
        )
    }
}
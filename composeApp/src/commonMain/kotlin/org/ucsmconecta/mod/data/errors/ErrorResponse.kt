package org.ucsmconecta.mod.data.errors

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String
)

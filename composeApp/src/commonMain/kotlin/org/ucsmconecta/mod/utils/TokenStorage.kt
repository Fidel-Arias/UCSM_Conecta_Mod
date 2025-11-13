package org.ucsmconecta.mod.utils

interface TokenStorage {
    suspend fun saveToken(token: String, role: String, id: Long)
    suspend fun getToken(): String?
    suspend fun getRole(): String?
    suspend fun getId(): Long
    suspend fun clear()
}

expect fun getTokenStorage(): TokenStorage
package org.ucsmconecta.mod.utils

import platform.Foundation.NSUserDefaults

class TokenStorageIOS: TokenStorage {
    private val prefs = NSUserDefaults.standardUserDefaults

    override suspend fun saveToken(token: String, role: String, id: Long) {
        prefs.setObject(token, forKey = "jwt_token")
        prefs.setObject(role, forKey = "role")
        prefs.setInteger(id, forKey = "id")
    }

    override suspend fun getToken(): String? = prefs.stringForKey("jwt_token")
    override suspend fun getRole(): String? = prefs.stringForKey("role")
    override suspend fun getId(): Long = prefs.integerForKey("id")
    override suspend fun clear() {
        prefs.removeObjectForKey("jwt_token")
        prefs.removeObjectForKey("role")
        prefs.removeObjectForKey("id")
    }
}
actual fun getTokenStorage(): TokenStorage {
    return TokenStorageIOS()
}
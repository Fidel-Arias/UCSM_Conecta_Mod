package org.ucsmconecta.mod.infra.errors

expect class ConnectionError(message: String) : Exception

class UnauthorizedException(message: String = "Token expirado o no autorizado"): Exception(message)
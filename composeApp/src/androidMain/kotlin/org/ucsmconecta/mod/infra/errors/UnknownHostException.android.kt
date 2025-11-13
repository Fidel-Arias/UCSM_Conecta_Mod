package org.ucsmconecta.mod.infra.errors

import java.net.UnknownHostException

actual class ConnectionError actual constructor(message: String) : UnknownHostException(message)
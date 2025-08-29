package org.ucsmconecta.mod.service.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isConnected: Flow<Boolean>
}

expect fun getNetworkMonitor(): NetworkMonitor
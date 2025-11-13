package org.ucsmconecta.mod.service.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.ucsmconecta.mod.activities.AsistantActivity

class AndroidNetworkMonitor(
    private val context: Context
) : NetworkMonitor {
    override val isConnected: Flow<Boolean> = callbackFlow {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Verificar conectividad real
                trySend(checkInternetAccess())
            }

            override fun onLost(network: Network) {
                trySend(false)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        // Estado inicial
        trySend(checkInternetAccess())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

    private fun checkInternetAccess(): Boolean {
        return try {
            val url = java.net.URL("https://clients3.google.com/generate_204")
            val connection = url.openConnection() as java.net.HttpURLConnection
            connection.setRequestProperty("User-Agent", "Android")
            connection.setRequestProperty("Connection", "close")
            connection.connectTimeout = 1500
            connection.connect()
            connection.responseCode == 204
        } catch (e: Exception) {
            false
        }
    }
}

lateinit var appContext: Context

fun initNetworkMonitor(context: Context) {
    appContext = context.applicationContext
}

actual fun getNetworkMonitor(): NetworkMonitor {
    return AndroidNetworkMonitor(context = appContext)
}
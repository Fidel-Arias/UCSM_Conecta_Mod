package org.ucsmconecta.mod.service.network

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.cinterop.*
import platform.SystemConfiguration.*
import platform.darwin.*

class IOSNetworkMonitor : NetworkMonitor {

    override val isConnected: Flow<Boolean> = callbackFlow {
        val reachability = SCNetworkReachabilityCreateWithName(null, "apple.com")

        val callback = staticCFunction { target, flags, info ->
            val isReachable = (flags.toInt() and kSCNetworkReachabilityFlagsReachable.toInt()) != 0 &&
                    (flags.toInt() and kSCNetworkReachabilityFlagsConnectionRequired.toInt()) == 0
            val channel = info!!.asCPointer<COpaque>()!!.asStableRef<SendChannel<Boolean>>().get()
            channel.trySend(isReachable)
        }

        val context = nativeHeap.alloc<SCNetworkReachabilityContext>().apply {
            version = 0
            info = StableRef.create(this@callbackFlow).asCPointer()
            retain = null
            release = null
            copyDescription = null
        }

        SCNetworkReachabilitySetCallback(reachability, callback, context.ptr)
        SCNetworkReachabilityScheduleWithRunLoop(
            reachability,
            CFRunLoopGetCurrent(),
            kCFRunLoopDefaultMode
        )

        // Estado inicial
        memScoped {
            val flags = alloc<SCNetworkReachabilityFlags>()
            if (SCNetworkReachabilityGetFlags(reachability, flags.ptr)) {
                val isReachable = (flags.value.toInt() and kSCNetworkReachabilityFlagsReachable.toInt()) != 0 &&
                        (flags.value.toInt() and kSCNetworkReachabilityFlagsConnectionRequired.toInt()) == 0
                trySend(isReachable)
            } else {
                trySend(false)
            }
        }

        awaitClose {
            SCNetworkReachabilityUnscheduleFromRunLoop(
                reachability,
                CFRunLoopGetCurrent(),
                kCFRunLoopDefaultMode
            )
            nativeHeap.free(context.rawPtr)
        }
    }.distinctUntilChanged()
}

actual fun getNetworkMonitor(): NetworkMonitor {
    return IOSNetworkMonitor()
}
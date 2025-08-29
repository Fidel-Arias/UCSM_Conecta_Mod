package org.ucsmconecta.mod.service.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

lateinit var appContext: Context

fun initSettingsApp(context: Context) {
    appContext = context.applicationContext
}

actual fun openAppSettings() {
    val context: Context = appContext
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}
package org.ucsmconecta.mod.service.settings

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openAppSettings() {
    val settingsUrl = NSURL.URLWithString("app-settings:")
    val app = UIApplication.sharedApplication
    if (settingsUrl != null && app.canOpenURL(settingsUrl)) {
        app.openURL(settingsUrl)
    }
}
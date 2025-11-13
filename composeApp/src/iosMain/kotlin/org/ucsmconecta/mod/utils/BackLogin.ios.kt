package org.ucsmconecta.mod.utils

import platform.UIKit.UIApplication

actual fun backLogin() {
    val rootController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootController?.dismissViewControllerAnimated(true, completion = null)
}
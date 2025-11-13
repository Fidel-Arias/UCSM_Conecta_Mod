package org.ucsmconecta.mod.utils

import android.content.Intent
import org.ucsmconecta.mod.ActivityHolder
import org.ucsmconecta.mod.activities.LoginActivity

actual fun backLogin() {
    // Recupera la actividad actual
    val currentActivity = ActivityHolder.currentActivity
    currentActivity?.let { activity ->
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)
        activity.finish()
    }
}
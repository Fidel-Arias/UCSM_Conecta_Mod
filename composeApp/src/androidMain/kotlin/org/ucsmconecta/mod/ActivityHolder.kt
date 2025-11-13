package org.ucsmconecta.mod

import android.app.Activity
import android.app.Application
import android.os.Bundle

object ActivityHolder : Application.ActivityLifecycleCallbacks {
    var currentActivity: Activity? = null

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (currentActivity == activity) currentActivity = null
    }

    // Los demás métodos  no son necesarios, pero deben existir:
    override fun onActivityCreated(a: Activity, s: Bundle?) {}
    override fun onActivityStarted(a: Activity) {}
    override fun onActivityStopped(a: Activity) {}
    override fun onActivitySaveInstanceState(a: Activity, b: Bundle) {}
    override fun onActivityDestroyed(a: Activity) {}
}
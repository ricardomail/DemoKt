package com.oasis.mydemokt.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import com.oasis.mydemokt.App

object LifecycleObj {
    private val TAG = App::class.java.name

    val instance = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onActivityCreated: " + activity.localClassName)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onActivityStarted: " + activity.localClassName)
        }

        override fun onActivityResumed(activity: Activity) {
            Log.d(TAG, "onActivityResumed: " + activity.localClassName)
        }

        override fun onActivityPaused(activity: Activity) {
            Log.d(TAG, "onActivityPaused: " + activity.localClassName)
        }

        override fun onActivityStopped(activity: Activity) {
            Log.d(TAG, "onActivityStopped: " + activity.localClassName)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            Log.d(TAG, "onActivitySaveInstanceState: " + activity.localClassName)
        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onActivityDestroyed: " + activity.localClassName)
        }

    }
}
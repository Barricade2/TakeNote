package com.tete.takenote

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

@HiltAndroidApp
class MainApplication : Application() {
    companion object {
        var context: WeakReference<Context>? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = WeakReference<Context>(applicationContext)
    }
}
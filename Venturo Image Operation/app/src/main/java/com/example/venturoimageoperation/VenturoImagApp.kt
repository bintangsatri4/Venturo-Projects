package com.example.venturoimageoperation

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class VenturoImageApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VenturoImageApp)
            modules(appModule)
        }
    }
}
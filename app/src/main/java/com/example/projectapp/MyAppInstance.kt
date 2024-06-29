package com.example.projectapp

import android.app.Application
import android.content.Context

class MyAppInstance: Application() {
    companion object {
        lateinit var instance: MyAppInstance
            private set

        val context: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}
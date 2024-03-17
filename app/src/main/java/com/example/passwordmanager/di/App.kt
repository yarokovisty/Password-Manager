package com.example.passwordmanager.di

import android.app.Application
import com.example.passwordmanager.domain.navigation.PasswordRouterImpl
import com.github.terrakok.cicerone.Cicerone
import dagger.hilt.android.HiltAndroidApp

class App : Application() {
    private val cicerone = Cicerone.create()
    private val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val passwordRouter = PasswordRouterImpl(router)

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }
}
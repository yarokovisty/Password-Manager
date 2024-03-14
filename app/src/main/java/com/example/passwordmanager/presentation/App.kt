package com.example.passwordmanager.presentation

import android.app.Application
import com.github.terrakok.cicerone.Cicerone

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
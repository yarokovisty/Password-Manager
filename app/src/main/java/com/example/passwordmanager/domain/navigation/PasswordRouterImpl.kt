package com.example.passwordmanager.domain.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

class PasswordRouterImpl(private val router: Router) : PasswordRouter {


    override fun replaceScreen(fragmentScreen: FragmentScreen) {
        router.replaceScreen(fragmentScreen)
    }

    override fun navigateTo(fragmentScreen: FragmentScreen) {
        router.navigateTo(fragmentScreen)
    }

    override fun backTo(fragmentScreen: FragmentScreen?) {
        router.backTo(fragmentScreen)
    }

    override fun exit() {
        router.exit()
    }

}
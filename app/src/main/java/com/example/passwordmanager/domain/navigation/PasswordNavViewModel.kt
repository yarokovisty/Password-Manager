package com.example.passwordmanager.domain.navigation

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.androidx.FragmentScreen

class PasswordNavViewModel(private val router: PasswordRouter) : ViewModel() {

    fun replaceScreen(fragmentScreen: FragmentScreen) {
        router.replaceScreen(fragmentScreen)
    }

    fun navigateTo(fragmentScreen: FragmentScreen) {
        router.navigateTo(fragmentScreen)
    }

    fun backTo(fragmentScreen: FragmentScreen? = null) {
        router.backTo(fragmentScreen)
    }

    fun exit() {
        router.exit()
    }


}
package com.example.passwordmanager.domain.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen


interface PasswordRouter  {
    fun replaceScreen(fragmentScreen: FragmentScreen)

    fun navigateTo(fragmentScreen: FragmentScreen)

    fun backTo(fragmentScreen: FragmentScreen?)

    fun exit()
}
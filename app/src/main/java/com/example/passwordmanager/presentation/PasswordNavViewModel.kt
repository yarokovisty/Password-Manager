package com.example.passwordmanager.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.androidx.FragmentScreen

class PasswordNavViewModel(private val router: PasswordRouter) : ViewModel() {

    fun replaceScreen(fragmentScreen: FragmentScreen) {
        router.replaceScreen(fragmentScreen)
    }

    fun navigateTo(fragmentScreen: FragmentScreen) {
        router.navigateTo(fragmentScreen)
    }

    fun backTo() {
        router.backTo()
    }


}
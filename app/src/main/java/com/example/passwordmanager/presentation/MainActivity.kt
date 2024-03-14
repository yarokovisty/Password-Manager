package com.example.passwordmanager.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navigatorHolder = App.INSTANCE.navigatorHolder
    private val navigator = AppNavigator(this, R.id.password_container)
    private val navViewModel = PasswordNavViewModel(App.INSTANCE.passwordRouter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navViewModel.replaceScreen(FragmentScreen{ PasswordListFragment() })
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }


}
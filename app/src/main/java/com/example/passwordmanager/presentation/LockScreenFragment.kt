package com.example.passwordmanager.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentLockScreenBinding
import com.example.passwordmanager.di.App
import com.example.passwordmanager.domain.navigation.PasswordNavViewModel
import com.github.terrakok.cicerone.androidx.FragmentScreen
import java.lang.StringBuilder
import java.util.concurrent.Executor

class LockScreenFragment : Fragment() {
    private var _binding: FragmentLockScreenBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var viewModel: PasswordLockViewModel
    private val navViewModel = PasswordNavViewModel(App.INSTANCE.passwordRouter)
    private var inputPassword = StringBuilder()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLockScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PasswordLockViewModel::class.java]
        addTextChangedListener()
        observeViewModel()
        onClick()

        if (viewModel.checkFirstLaunch()) {
            launchFirstTime()
        } else {
            launchNoFirstTime()
        }






    }

    private fun observeViewModel() = with(viewModel) {
        errorInputPass.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input)
            } else {
                null
            }
            binding.tilPasswordLockScr.error = message
        }
        errorInputConfirm.observe(viewLifecycleOwner) {
            errorInputConfirm.observe(viewLifecycleOwner) {
                val message = if (it) {
                    getString(R.string.error_input)
                } else {
                    null
                }
                binding.tilConfirmPassword.error = message
            }
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            navViewModel.replaceScreen(FragmentScreen { PasswordListFragment() })
        }
    }

    private fun launchFirstTime() = with(binding) {
        scrFirstTime.isVisible = true
        scrNoFirstTIme.isVisible = false

        with(binding) {
            btnReadyLockScr.setOnClickListener {
                viewModel.savePassword(
                    etPasswordLockScr.text.toString(),
                    etConfirmPassword.text.toString()
                )
            }
        }

    }

    private fun launchNoFirstTime() = with(binding) {
        scrFirstTime.isVisible = false
        scrNoFirstTIme.isVisible = true

        setupBiometric()
    }


    private fun addTextChangedListener() = with(binding) {
        etPasswordLockScr.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPass()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputConfirm()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }


    private fun onClick() = with(binding) {
        btnNum1.setOnClickListener {
            addNumber("1")
        }
        btnNum2.setOnClickListener {
            addNumber("2")
        }
        btnNum3.setOnClickListener {
            addNumber("3")
        }
        btnNum4.setOnClickListener {
            addNumber("4")
        }
        btnNum5.setOnClickListener {
            addNumber("5")
        }
        btnNum6.setOnClickListener {
            addNumber("6")
        }
        btnNum7.setOnClickListener {
            addNumber("7")
        }
        btnNum8.setOnClickListener {
            addNumber("8")
        }
        btnNum9.setOnClickListener {
            addNumber("9")
        }
        btnNum0.setOnClickListener {
            addNumber("0")
        }
        btnDelete.setOnClickListener {
            deleteNumber()
        }
        btnFingerPrint.setOnClickListener {
            setupBiometric()
        }

    }

    private fun addNumber(num: String) {
        if (inputPassword.length < 3) {
            inputPassword.append(num)
            addDrawInputPassword()
        } else {
            inputPassword.append(num)
            addDrawInputPassword()
            val validPassword = viewModel.getPassword() == inputPassword.toString()
            if (validPassword) {
                navViewModel.replaceScreen(FragmentScreen { PasswordListFragment() })
            } else {
                inputPassword.clear()
                manageEnabled(false)
                drawPoint(R.drawable.icon_point_pass_wrong)
                vibrate()


                view?.postDelayed({
                    drawPoint(R.drawable.icon_point_pass)
                    manageEnabled(true)
                }, DELAY)


            }
        }
    }

    private fun deleteNumber() {
        if (inputPassword.isNotEmpty()) {
            inputPassword.deleteCharAt(inputPassword.length - 1)
            delDrawInputPassword()
        }
    }

    private fun addDrawInputPassword() = with(binding) {
        when (inputPassword.length) {
            1 -> imgPass1.setImageResource(R.drawable.icon_point_pass_input)
            2 -> imgPass2.setImageResource(R.drawable.icon_point_pass_input)
            3 -> imgPass3.setImageResource(R.drawable.icon_point_pass_input)
            4 -> imgPass4.setImageResource(R.drawable.icon_point_pass_input)
        }
    }

    private fun delDrawInputPassword() = with(binding) {
        when (inputPassword.length) {
            0 -> imgPass1.setImageResource(R.drawable.icon_point_pass)
            1 -> imgPass2.setImageResource(R.drawable.icon_point_pass)
            2 -> imgPass3.setImageResource(R.drawable.icon_point_pass)
        }
    }

    private fun drawPoint(id: Int) = with(binding) {
        imgPass1.setImageResource(id)
        imgPass2.setImageResource(id)
        imgPass3.setImageResource(id)
        imgPass4.setImageResource(id)
    }

    private fun vibrate() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    TIME_VIBRATE,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(TIME_VIBRATE)
        }
    }

    private fun manageEnabled(enabled: Boolean) = with(binding) {
        for (i in 0 until glInputPassword.childCount) {
            val child = glInputPassword.getChildAt(i)
            child.isEnabled = enabled
        }
    }

    private fun setupBiometric() {
        val biometricManager = BiometricManager.from(requireContext())

        if (biometricManager
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            executor = ContextCompat.getMainExecutor(requireContext())
            biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)

                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        navViewModel.replaceScreen(FragmentScreen{ PasswordListFragment() })
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()

                    }
                })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.app_name))
                .setNegativeButtonText(getString(R.string.cancel))
                .build()

            biometricPrompt.authenticate(promptInfo)

        }
    }

    companion object {
        private const val DELAY = 750L
        private const val TIME_VIBRATE = 500L
    }

}
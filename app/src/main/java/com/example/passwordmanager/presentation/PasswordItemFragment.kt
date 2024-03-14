package com.example.passwordmanager.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentPasswordItemBinding


class PasswordItemFragment : Fragment() {
    private var _binding: FragmentPasswordItemBinding? = null
    private val binding
        get() = _binding!!
    private val navViewModel = PasswordNavViewModel(App.INSTANCE.passwordRouter)
    private lateinit var passwordItemViewModel: PasswordItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passwordItemViewModel = ViewModelProvider(this)[PasswordItemViewModel::class.java]

        parseParams()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseParams() {

        when(arguments?.getString(EXTRA_MODE) ?: "") {
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchAddMode() {
        binding.btnSave.setOnClickListener {
            insertDataToDatabase()
            navViewModel.backTo()
        }
    }

    private fun insertDataToDatabase() = with(binding) {
        val name = etName.text.toString()
        val url = etUrl.text.toString()
        val login = etLogin.text.toString()
        val password = etPassword.text.toString()

        passwordItemViewModel.addPasswordItem(
            name,
            url,
            login,
            password
        )
        Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG)
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
    }


}
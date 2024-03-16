package com.example.passwordmanager.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.passwordmanager.databinding.FragmentPasswordItemBinding
import com.example.passwordmanager.di.App
import com.example.passwordmanager.domain.PasswordItem
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.launch


class PasswordItemFragment : Fragment() {
    private var _binding: FragmentPasswordItemBinding? = null
    private val binding
        get() = _binding!!
    private val navViewModel = PasswordNavViewModel(App.INSTANCE.passwordRouter)
    private lateinit var passwordItemViewModel: PasswordItemViewModel
    private var mode = MODE_UNKNOWN
    private var itemId = UNKNOWN_ID


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

        binding.tbPasswordItemAdd.setNavigationOnClickListener {
            navViewModel.exit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseParams() {

        if (arguments?.getString(EXTRA_MODE) == MODE_ADD) {
            launchAddMode()
            mode = MODE_ADD
        }
        else if (arguments?.getInt(MODE_EDIT, -1) != -1) {
            launchEditMode()
            mode = MODE_EDIT
        }

    }

    private fun launchAddMode() {
        binding.btnSave.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun launchEditMode() {
        itemId = arguments?.getInt(MODE_EDIT)!!
        passwordItemViewModel.getPasswordItem(itemId)

        passwordItemViewModel.passwordItem.observe(viewLifecycleOwner) {
            with(binding) {
                etName.setText(it.nameSite)
                etLogin.setText(it.login)
                etPassword.setText(it.password)
                etUrl.setText(it.urlSite)
                setIcon(it.imgSite)
            }
        }

        binding.btnSave.setOnClickListener {
            updateDataToDatabase()
        }
    }

    private fun setIcon(urlIcon: String) {
        Glide.with(requireContext())
            .load(urlIcon)
            .into(binding.ivSite)
    }

    private fun insertDataToDatabase() = with(binding) {
        val name = etName.text.toString()
        val url = etUrl.text.toString()
        val login = etLogin.text.toString()
        val password = etPassword.text.toString()


        with(passwordItemViewModel) {
            urlIcon.observe(viewLifecycleOwner) {
                passwordItemViewModel.addPasswordItem(
                    name,
                    url,
                    login,
                    password,
                    it
                )
                navViewModel.backTo()
            }
            getUrlIcon(url)
        }


    }

    private fun updateDataToDatabase() = with(binding) {
        val name = etName.text.toString()
        val url = etUrl.text.toString()
        val login = etLogin.text.toString()
        val password = etPassword.text.toString()

        with(passwordItemViewModel) {
            urlIcon.observe(viewLifecycleOwner) {
                passwordItemViewModel.editPasswordItem(
                    name,
                    url,
                    login,
                    password,
                    it
                )
                navViewModel.backTo()
            }
            getUrlIcon(url)
        }
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val UNKNOWN_ID = -1




    }


}
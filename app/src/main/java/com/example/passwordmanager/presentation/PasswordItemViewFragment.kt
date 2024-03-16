package com.example.passwordmanager.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentPasswordItemViewBinding
import com.example.passwordmanager.di.App
import com.github.terrakok.cicerone.androidx.FragmentScreen

class PasswordItemViewFragment : Fragment() {
    private var _binding: FragmentPasswordItemViewBinding? = null
    private val binding
        get() = _binding!!
    private val navViewModel = PasswordNavViewModel(App.INSTANCE.passwordRouter)
    private lateinit var passwordItemViewModel: PasswordItemViewModel
    private var itemId = UNKNOWN_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordItemViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passwordItemViewModel = ViewModelProvider(this)[PasswordItemViewModel::class.java]
        parseParams()
        observeViewModel()

        with(binding) {
            tbViewPasswordItem.setNavigationOnClickListener {
                navViewModel.backTo()
            }
            etLogin.setOnClickListener {
                copyText(etLogin.text.toString(), LOGIN_LABEL)
            }
            etPassword.setOnClickListener {
                copyText(etPassword.text.toString(), PASSWORD_LABEL)
            }
            etUrl.setOnClickListener {
                copyText(etUrl.text.toString(), URL_LABEL)
            }
            btnChange.setOnClickListener {
                navViewModel.navigateTo(FragmentScreen{ newBundleChangeItem(itemId) })
            }
        }

    }

    private fun parseParams() {
        itemId = arguments?.getInt(EXTRA_SHOP_ITEM_ID)!!
        passwordItemViewModel.getPasswordItem(itemId)
    }

    private fun setIcon(urlIcon: String) {
        Glide.with(requireContext())
            .load(urlIcon)
            .into(binding.imgSite)
    }

    private fun observeViewModel() {
        passwordItemViewModel.passwordItem.observe(viewLifecycleOwner) {
            with(binding) {
                tvName.text = it.nameSite
                etLogin.setText(it.login)
                etPassword.setText(it.password)
                etUrl.setText(it.urlSite)
                setIcon(it.imgSite)
            }
        }
    }

    private fun copyText(textToCopy: String, label: String) {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager
        val clip = ClipData.newPlainText(label, textToCopy)
        clipboardManager.setPrimaryClip(clip)
    }

    companion object {
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val LOGIN_LABEL = "Login"
        private const val PASSWORD_LABEL = "Password"
        private const val URL_LABEL = "Url"
        private const val UNKNOWN_ID = -1

        fun newBundleChangeItem(itemId: Int): PasswordItemFragment {
            return PasswordItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(MODE_EDIT, itemId)
                }
            }
        }
    }
}
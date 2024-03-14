package com.example.passwordmanager.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.passwordmanager.R
import com.example.passwordmanager.data.remote.ParsingImgRepositoryImpl
import com.example.passwordmanager.databinding.FragmentPasswordListBinding
import com.example.passwordmanager.domain.PasswordItem
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.launch

class PasswordListFragment : Fragment() {
    private var _binding: FragmentPasswordListBinding? = null
    private val binding: FragmentPasswordListBinding
        get() = _binding!!
    private lateinit var adapter: PasswordListAdapter
    private val navViewModel = PasswordNavViewModel(App.INSTANCE.passwordRouter)
    private lateinit var passwordListViewModel: PasswordListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()

        binding.btnAddPassItem.setOnClickListener {
            navViewModel.navigateTo(FragmentScreen{ newBundleAddItem() })
            newBundleAddItem()
        }

        passwordListViewModel = ViewModelProvider(this)[PasswordListViewModel::class.java]
        passwordListViewModel.passwordList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecycleView() {
        adapter = PasswordListAdapter()
        binding.rvPassList.adapter = adapter
        binding.rvPassList.recycledViewPool.setMaxRecycledViews(
            R.layout.item_password,
            PasswordListAdapter.MAX_POOL_SIZE
        )
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newBundleAddItem() : PasswordItemFragment {
            return PasswordItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_ADD)
                }
            }

        }

        fun newBundleEditItem() : PasswordItemFragment {
            return PasswordItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_EDIT)
                }
            }
        }
    }
}
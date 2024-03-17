package com.example.passwordmanager.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.R
import com.example.passwordmanager.databinding.FragmentPasswordListBinding
import com.example.passwordmanager.di.App
import com.example.passwordmanager.domain.navigation.PasswordNavViewModel
import com.github.terrakok.cicerone.androidx.FragmentScreen

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
        setupSwipeListener()
        setupClickListener()

        passwordListViewModel = ViewModelProvider(this)[PasswordListViewModel::class.java]
        passwordListViewModel.passwordList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnAddPassItem.setOnClickListener {
            navViewModel.navigateTo(FragmentScreen { newBundleAddItem() })
            newBundleAddItem()
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

    private fun setupSwipeListener() {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                passwordListViewModel.deletePasswordItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvPassList)
    }

    private fun setupClickListener() {
        adapter.onPasswordItemClickListener = { passwordItem ->
            navViewModel.navigateTo(FragmentScreen{newBundleViewItem(passwordItem.id)})
        }
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"

        fun newBundleAddItem(): PasswordItemFragment {
            return PasswordItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_ADD)
                }
            }

        }


        fun newBundleViewItem(id: Int): PasswordItemViewFragment {
            return PasswordItemViewFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_SHOP_ITEM_ID, id)
                }
            }
        }

    }
}
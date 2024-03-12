package com.example.passwordmanager.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.passwordmanager.data.remote.ParsingImgImpl
import com.example.passwordmanager.data.remote.ParsingImgImpl.extractIconLinkFromHtml
import com.example.passwordmanager.data.remote.ParsingImgImpl.fetchHtml
import com.example.passwordmanager.databinding.FragmentPasswordListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordListFragment : Fragment() {
    private var _binding: FragmentPasswordListBinding? = null
    private val binding: FragmentPasswordListBinding
        get() = _binding!!
    private var adapter: PasswordListAdapter? = null


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





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }
}
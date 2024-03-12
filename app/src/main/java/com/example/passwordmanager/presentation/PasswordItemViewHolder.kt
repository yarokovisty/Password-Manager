package com.example.passwordmanager.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.databinding.ItemPasswordBinding
import com.example.passwordmanager.domain.PasswordItem

class PasswordItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPasswordBinding.bind(view)

    fun bind(passwordItem: PasswordItem) {
        binding.tvNameSite.text = passwordItem.nameSite
    }
}
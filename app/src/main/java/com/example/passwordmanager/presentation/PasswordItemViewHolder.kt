package com.example.passwordmanager.presentation

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.passwordmanager.R
import com.example.passwordmanager.data.remote.RemoteRepositoryImpl
import com.example.passwordmanager.databinding.ItemPasswordBinding
import com.example.passwordmanager.domain.PasswordItem

class PasswordItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPasswordBinding.bind(view)

    fun bind(passwordItem: PasswordItem) = with(binding) {
        tvNameSite.text = passwordItem.nameSite
        if (passwordItem.imgSite.isNotEmpty()) {
            Glide.with(itemView.context)
                .load(passwordItem.imgSite)
                .into(iconSite)
        } else {
            iconSite.setImageResource(R.drawable.icon_img)
        }


    }
}
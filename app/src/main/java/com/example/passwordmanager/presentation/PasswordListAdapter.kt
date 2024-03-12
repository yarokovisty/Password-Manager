package com.example.passwordmanager.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.passwordmanager.R
import com.example.passwordmanager.domain.PasswordItem

class PasswordListAdapter : ListAdapter<PasswordItem, PasswordItemViewHolder>(PasswordItemDiffCallback()) {

    var onPasswordItemLongClickListener: ((PasswordItem) -> Unit)? = null
    var onPasswordItemClickListener: ((PasswordItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_password,
            parent,
            false
        )

        return PasswordItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordItemViewHolder, position: Int) {
        val passwordItem = getItem(position)
        holder.bind(passwordItem)

        holder.itemView.setOnClickListener {
            onPasswordItemClickListener?.invoke(passwordItem)
        }

        holder.itemView.setOnLongClickListener {
            onPasswordItemLongClickListener?.invoke(passwordItem)
            true
        }
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }
}
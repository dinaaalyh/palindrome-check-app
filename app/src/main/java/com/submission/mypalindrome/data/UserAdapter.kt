package com.submission.mypalindrome.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.mypalindrome.data.remote.response.DataItem
import com.submission.mypalindrome.databinding.ItemUserBinding

class UserAdapter(
    private val itemClickListener: OnItemClickListener
) : PagingDataAdapter<DataItem, UserAdapter.ViewHolder>(UserItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val itemBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: DataItem) {
            val displayName = "${user.firstName} ${user.lastName}"
            itemBinding.tvItemName.text = displayName
            itemBinding.tvEmail.text = user.email
            Glide.with(itemBinding.root.context).load(user.avatar).into(itemBinding.UserImg)

            itemBinding.root.setOnClickListener {
                itemClickListener.onItemClick(user)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user: DataItem)
    }

    private class UserItemDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}

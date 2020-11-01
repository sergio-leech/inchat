package com.example.inchat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inchat.databinding.LeftItemBinding
import com.example.inchat.databinding.RightItemBinding
import com.example.inchat.model.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallbackUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                LeftItemHolder(
                    LeftItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                RightItemHolder(
                    RightItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> (holder as LeftItemHolder).bind(getItem(position))
            else -> (holder as RightItemHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val firebaseUser = Firebase.auth.currentUser?.uid
        return when (getItem(position).senderId) {
            firebaseUser -> 0
            else -> 1
        }
    }

    class LeftItemHolder(private val binding: LeftItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemMessage: Message) {
            binding.apply {
                message = itemMessage
                executePendingBindings()
            }
        }
    }

    class RightItemHolder(private val binding: RightItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemMessage: Message) {
            binding.apply {
                message = itemMessage
                executePendingBindings()
            }
        }
    }

    companion object DiffCallbackUtils : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.receiverId == newItem.receiverId
        }
    }
}
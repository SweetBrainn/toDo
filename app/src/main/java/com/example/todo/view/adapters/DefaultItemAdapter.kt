package com.example.todo.view.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ItemDefaultListBinding
import com.example.todo.model.database.entity.Item


class DefaultItemAdapter :
    ListAdapter<Item, DefaultItemAdapter.DefaultItemViewHolder>(object :
        DiffUtil.ItemCallback<Item>() {
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.title == newItem.title && oldItem.isDone == newItem.isDone

        }

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
    }) {

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultItemViewHolder {
        return DefaultItemViewHolder(
            ItemDefaultListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DefaultItemViewHolder, position: Int) {
        if (getItem(position) != null)
            holder.bind(getItem(position))
    }


    inner class DefaultItemViewHolder(private val itemBinding: ItemDefaultListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Item) {
            itemBinding.item = item
            itemBinding.radioButton.setOnClickListener {
                item.isDone = !item.isDone
                callback?.onRadioButtonClick(item)
                notifyItemChanged(position)
            }
        }


    }

    interface Callback {
        fun onRadioButtonClick(item: Item)
    }

}
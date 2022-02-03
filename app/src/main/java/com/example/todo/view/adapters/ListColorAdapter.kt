package com.example.todo.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.ItemColorListBinding
import com.example.todo.model.enums.ReminderListColor


class ListColorAdapter :
    ListAdapter<ReminderListColor, ListColorAdapter.ListColorViewHolder>(object :
        DiffUtil.ItemCallback<ReminderListColor>() {
        override fun areContentsTheSame(
            oldItem: ReminderListColor,
            newItem: ReminderListColor
        ): Boolean {
            return false

        }

        override fun areItemsTheSame(
            oldItem: ReminderListColor,
            newItem: ReminderListColor
        ): Boolean {
            return false
        }
    }) {

    var onClickListener: ListColorOnclickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListColorViewHolder {
        return ListColorViewHolder(
            ItemColorListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListColorViewHolder, position: Int) {
        if (getItem(position) != null)
            holder.bind(getItem(position))
    }


    inner class ListColorViewHolder(private val itemBinding: ItemColorListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: ReminderListColor) {
            itemBinding.viewList.setBackgroundColor(
                itemBinding.root.context.getColor(
                    item.darkColor
                )
            )
            itemBinding.btnList.setOnClickListener {
                it.startAnimation(
                    AnimationUtils.loadAnimation(itemBinding.root.context, R.anim.increase_size)
                )
                onClickListener?.onclick(item)
            }
            if (item == ReminderListColor.DEFAULT)
                itemBinding.btnList.performClick()
        }

    }

    interface ListColorOnclickListener {
        fun onclick(item: ReminderListColor)
    }

}

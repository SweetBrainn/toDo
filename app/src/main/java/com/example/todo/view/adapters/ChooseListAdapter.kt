package com.example.todo.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ItemChooseListBinding
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.enums.ReminderListColor

class ChooseListAdapter :
    ListAdapter<ItemList, ChooseListAdapter.ChooseListViewHolder>(object :
        DiffUtil.ItemCallback<ItemList>() {
        override fun areContentsTheSame(
            oldItem: ItemList,
            newItem: ItemList
        ): Boolean {
            return false

        }

        override fun areItemsTheSame(
            oldItem: ItemList,
            newItem: ItemList
        ): Boolean {
            return false
        }
    }) {

    var onClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChooseListAdapter.ChooseListViewHolder =
        ChooseListViewHolder(
            ItemChooseListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ChooseListAdapter.ChooseListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ChooseListViewHolder(private val viewBinding: ItemChooseListBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: ItemList) {
            viewBinding.setVariable(BR.item, item)
            viewBinding.viewIconItem.setBackgroundColor(
                viewBinding.root.context.getColor(
                    ReminderListColor.getColorByName(
                        item.color
                    ).darkColor
                )
            )
            viewBinding.mainView.setOnClickListener {
                onClickListener?.onClick(item)
            }
            viewBinding.executePendingBindings()

        }
    }

    interface OnItemClickListener {
        fun onClick(item: ItemList)
    }

}
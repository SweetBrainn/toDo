package com.example.todo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.Utility.show
import com.example.todo.model.database.entity.ListWithItems
import com.example.todo.model.enums.ReminderListColor

class HorizontalAdapter(val context: Context) :
    ListAdapter<ListWithItems, HorizontalAdapter.HorizontalViewHolder>(object :
        DiffUtil.ItemCallback<ListWithItems>() {
        override fun areContentsTheSame(oldItem: ListWithItems, newItem: ListWithItems): Boolean {
            return oldItem.items == newItem.items
        }

        override fun areItemsTheSame(oldItem: ListWithItems, newItem: ListWithItems): Boolean {
            return oldItem.list.id == newItem.list.id
        }
    }) {


    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val inflater = LayoutInflater.from(context)
        return HorizontalViewHolder(
            inflater.inflate(
                R.layout.item_horizontal_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class HorizontalViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val cardView = itemView.findViewById<CardView>(R.id.cardView)

        fun bind(item: ListWithItems) {

            val txtListTitle = itemView.findViewById<TextView>(R.id.txtListTitle)
            val txtReminderCount = itemView.findViewById<TextView>(R.id.txtReminderCount)
            val backGroundColorId = ReminderListColor.getColorByName(item.list.color).darkColor


            txtListTitle.text = item.list.title
            txtListTitle.show()
            txtReminderCount.text = item.items.size.toString()

            try {
                cardView.setCardBackgroundColor(context.resources.getColor(backGroundColorId))
            } catch (e: Exception) {
                cardView.setCardBackgroundColor(context.resources.getColor(R.color.default_light))
            }

            cardView.setOnClickListener {
                onItemClickListener?.onClick(item)
            }


        }

    }

    interface OnItemClickListener {
        fun onClick(item: ListWithItems)
    }

}


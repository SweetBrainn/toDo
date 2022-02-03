package com.example.todo.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.todo.R
import com.example.todo.databinding.FragmentListItemBinding
import com.example.todo.model.database.entity.Item
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.database.entity.ListWithItems
import com.example.todo.model.enums.BundleKey
import com.example.todo.model.enums.ReminderListColor
import com.example.todo.view.adapters.DefaultItemAdapter
import com.example.todo.viewModel.ListItemViewModel

class ListItemFragment :
    BaseFragment<FragmentListItemBinding, ListItemViewModel>(
        R.layout.fragment_list_item,
        ListItemViewModel::class.java
    ) {


    private val adapter = DefaultItemAdapter()
    private var selectedListItem: ItemList? = null
    private lateinit var colorType: ReminderListColor
    override fun initLayout(view: View) {
        super.initLayout(view)
        arguments?.let {
            val passedData = it.getSerializable(BundleKey.SELECTED_LIST_ITEM.name) as ListWithItems
            selectedListItem = passedData.list
            getViewModel().initData(passedData)
            colorType = ReminderListColor.getColorByName(getViewModel().passedData.list.color)
            initView()
        }

        getViewModel().remindersLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        getViewBinding().imgAddReminder.setOnClickListener {
            navigationTo(R.id.action_listItemFragment_to_addReminderFragment, Bundle().apply {
                putSerializable(BundleKey.SELECTED_LIST_ITEM.name, selectedListItem)
            })
        }
        getViewBinding().txtAddReminder.setOnClickListener {
            getViewBinding().imgAddReminder.performClick()
        }
    }


    private fun initView() {
        getViewBinding().lstItems.adapter = adapter
        getViewBinding().viewHeader.backgroundTintList =
            context?.resources?.getColorStateList(colorType.lightColor)
        adapter.callback = object : DefaultItemAdapter.Callback {
            override fun onRadioButtonClick(item: Item) {
                getViewModel().updateItems(item)
            }
        }
    }

    override fun initToolbar() {

        setToolbarColor(colorType.lightColor)

        setToolbarTitle("")


        handleToolbarBackIconVisibility(true)
        setToolbarBackIconColor(R.color.textColorBold)

        handleToolbarImgVisibility(false)
        handleToolbarActionVisibility(false)
    }

}
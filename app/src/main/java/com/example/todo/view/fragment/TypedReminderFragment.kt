package com.example.todo.view.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.todo.R
import com.example.todo.databinding.FragmentTypedRemindersBinding
import com.example.todo.model.Utility.Utility
import com.example.todo.model.database.entity.Item
import com.example.todo.model.enums.BundleKey
import com.example.todo.model.enums.CustomReminderType
import com.example.todo.view.adapters.DefaultItemAdapter
import com.example.todo.viewModel.TypedReminderViewModel

class TypedReminderFragment :
    BaseFragment<FragmentTypedRemindersBinding, TypedReminderViewModel>(
        R.layout.fragment_typed_reminders,
        TypedReminderViewModel::class.java
    ) {

    private lateinit var adapter: DefaultItemAdapter

    override fun initLayout(view: View) {
        super.initLayout(view)
        getViewModel().showLoading(true)
        handlePassedData(arguments)
        initRecyclerView()
        initView()
        getViewModel().getItems()

        getViewModel().remindersFromDB.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            getViewModel().showLoading(false)
            if (Utility.isNotNullOrEmpty(it)) {
                adapter.submitList(getViewModel().reminders)
                getViewModel().listVisibility.postValue(true)
                getViewModel().remindersFromDB.postValue(null)
            }
        })

        getViewBinding().txtAddReminder.setOnClickListener {
            navigationTo(R.id.action_typedReminderFragment_to_addReminderFragment)
        }

        getViewBinding().imgAddReminder.setOnClickListener { getViewBinding().txtAddReminder.performClick() }


    }

    private fun handlePassedData(arguments: Bundle?) {
        arguments?.let {
            getViewModel().toolbarTitle = it.getString(BundleKey.TOOLBAR_TITLE.name).toString()
            getViewModel().toolbarBackgroundColor =
                it.getInt(BundleKey.TOOLBAR_BACKGROUND_COLOR.name)

            getViewModel().toolbarTextColor = it.getInt(BundleKey.TOOLBAR_TEXT_COLOR.name)

            getViewModel().toolbarIcon = it.getInt(BundleKey.TOOLBAR_ICON.name)

            getViewModel().customReminderType =
                it.getSerializable(BundleKey.CUSTOM_REMINDER_TYPE.name) as CustomReminderType

        }
    }

    private fun initRecyclerView() {
        adapter = DefaultItemAdapter()
        getViewBinding().lstItems.adapter = adapter
        adapter.callback = object : DefaultItemAdapter.Callback {
            override fun onRadioButtonClick(item: Item) {
                getViewModel().updateItem(item)
            }
        }
    }


    private fun initView() {

        if (getViewModel().toolbarTextColor != 0) {
            getViewBinding().txtAddReminder.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    getViewModel().toolbarTextColor
                )
            )
            getViewBinding().imgAddReminder.setColorFilter(
                ContextCompat.getColor(
                    requireActivity(),
                    getViewModel().toolbarTextColor
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
        }


    }

    override fun initToolbar() {

        setToolbarTitle(getViewModel().toolbarTitle)

        handleToolbarBackIconVisibility(true)

        handleToolbarActionVisibility(false)

        if (getViewModel().toolbarBackgroundColor != 0)
            setToolbarColor(getViewModel().toolbarBackgroundColor)

        if (getViewModel().toolbarTextColor != 0) {
            setToolbarTextColor(getViewModel().toolbarTextColor)
            setToolbarBackIconColor(getViewModel().toolbarTextColor)
        }

        if (getViewModel().toolbarIcon != 0) {
            setToolbarImgIcon(getViewModel().toolbarIcon)
            handleToolbarImgVisibility(true)
        }

    }

}
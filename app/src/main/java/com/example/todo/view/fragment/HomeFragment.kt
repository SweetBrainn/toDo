package com.example.todo.view.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.example.todo.R
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.model.Utility.showLongToast
import com.example.todo.model.database.entity.ListWithItems
import com.example.todo.model.enums.BundleKey
import com.example.todo.model.enums.CustomReminderType
import com.example.todo.view.adapters.HorizontalAdapter
import com.example.todo.viewModel.HomeViewModel
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    HomeViewModel::class.java
) {


    private lateinit var gridAdapter: HorizontalAdapter


    override fun initLayout(view: View) {
        super.initLayout(view)
        getViewModel().resetViewModel()
        initRecyclerView()
        getViewModel().showLoading(true)
        getViewModel().getAllData()
        getViewModel().listFromDB.observe(viewLifecycleOwner, Observer {
            it?.let {
                gridAdapter.submitList(it.toMutableList())
                getViewModel().listFromDB.postValue(null)
                getViewModel().showLoading(false)
            }
        })
        getViewBinding().viewToday.setOnClickListener { todayViewSelected() }
        getViewBinding().viewDone.setOnClickListener { doneViewSelected() }
        getViewBinding().viewAll.setOnClickListener { allViewSelected() }
        getViewBinding().viewSchedule.setOnClickListener { scheduleViewSelected() }
        getViewBinding().viewUnDone.setOnClickListener { unDoneViewSelected() }
        getViewBinding().txtAddList.setOnClickListener { navigationTo(R.id.action_homeFragment_to_addListFragment) }
        getViewBinding().icAdd.setOnClickListener { navigateToAddReminder() }
        getViewBinding().txtNewReminder.setOnClickListener { getViewBinding().icAdd.performClick() }
    }

    private fun navigateToAddReminder() {
        navigationTo(R.id.action_homeFragment_to_addReminderFragment)
    }

    private fun unDoneViewSelected() {
        navigateToReminderManagement(
            R.id.action_homeFragment_to_typedReminderFragment,
            toolbarTitle = "ToDo Reminders",
            toolbarIcon = R.drawable.ic_todocircle,
            toolbarTextColor = R.color.cool_purple,
            customReminderType = CustomReminderType.TODO
        )
    }

    private fun scheduleViewSelected() {
        navigateToReminderManagement(
            R.id.action_homeFragment_to_typedReminderFragment,
            toolbarTitle = "Scheduled Reminders",
            toolbarIcon = R.drawable.ic_calendarcircle,
            toolbarTextColor = R.color.cool_red,
            customReminderType = CustomReminderType.SCHEDULED
        )
    }

    private fun allViewSelected() {
        navigateToReminderManagement(
            R.id.action_homeFragment_to_typedReminderFragment,
            toolbarTitle = "All Reminders",
            toolbarIcon = R.drawable.ic_inboxcircle,
            toolbarTextColor = R.color.cool_gray,
            customReminderType = CustomReminderType.DEFAULT
        )
    }

    private fun doneViewSelected() {
        navigateToReminderManagement(
            R.id.action_homeFragment_to_typedReminderFragment,
            toolbarTitle = "Done Reminders",
            toolbarIcon = R.drawable.ic_donecircle,
            toolbarTextColor = R.color.cool_green,
            customReminderType = CustomReminderType.DONE
        )
    }

    private fun todayViewSelected() {
        navigateToReminderManagement(
            R.id.action_homeFragment_to_typedReminderFragment,
            toolbarTitle = "Today Reminders",
            toolbarIcon = R.drawable.ic_todaycircle,
            toolbarTextColor = R.color.cool_blue,
            customReminderType = CustomReminderType.TODAY
        )
    }

    private fun initRecyclerView() {
        getViewBinding().listGrid.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        gridAdapter = HorizontalAdapter(requireContext()).apply {
            submitList(getViewModel().allData.toMutableList())
        }
        getViewBinding().listGrid.adapter = gridAdapter

        gridAdapter.onItemClickListener = object : HorizontalAdapter.OnItemClickListener {
            override fun onClick(item: ListWithItems) {
                navigationTo(R.id.action_homeFragment_to_listItemFragment, Bundle().apply {
                    putSerializable(BundleKey.SELECTED_LIST_ITEM.name, item)
                })
            }
        }

    }

    private fun navigateToReminderManagement(
        @IdRes action: Int,
        toolbarTitle: String? = null,
        @ColorRes toolbarBackgroundColor: Int? = null,
        @ColorRes toolbarTextColor: Int? = null,
        @DrawableRes toolbarIcon: Int? = null,
        customReminderType: CustomReminderType? = null
    ) {
        navigationTo(action, Bundle().apply {
            putString(BundleKey.TOOLBAR_TITLE.name, toolbarTitle)
            toolbarBackgroundColor?.let {
                this.putInt(BundleKey.TOOLBAR_BACKGROUND_COLOR.name, toolbarBackgroundColor)
            }
            toolbarTextColor?.let {
                this.putInt(BundleKey.TOOLBAR_TEXT_COLOR.name, toolbarTextColor)
            }
            toolbarIcon?.let {
                this.putInt(BundleKey.TOOLBAR_ICON.name, toolbarIcon)
            }
            customReminderType?.let {
                this.putSerializable(BundleKey.CUSTOM_REMINDER_TYPE.name, customReminderType)
            }

        })
    }

    override fun initToolbar() {
        setToolbarTitle("Sweet Reminder")
        setToolbarColor(R.color.colorPrimary)
        handleToolbarImgVisibility(true)
        setToolbarImgIcon(R.drawable.ic_monday)
        handleToolbarBackIconVisibility(false)
        handleToolbarActionVisibility(false)
        setToolbarTextColor(R.color.textColorBold)
    }

}
package com.example.todo.view.fragment

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.todo.R
import com.example.todo.databinding.FragmentAddReminderBinding
import com.example.todo.model.Utility.Utility
import com.example.todo.model.Utility.gone
import com.example.todo.model.Utility.show
import com.example.todo.model.database.entity.Item
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.enums.BundleKey
import com.example.todo.model.enums.ReminderListColor
import com.example.todo.view.dialogs.ChooseListDialogFragment
import com.example.todo.viewModel.AddReminderViewModel
import java.util.*

class AddReminderFragment : BaseFragment<FragmentAddReminderBinding, AddReminderViewModel>(
    R.layout.fragment_add_reminder,
    AddReminderViewModel::class.java
) {


    private lateinit var selectedList: ItemList
    var selectedDate = Date()
    var selectedDateTitle = Utility.dateToString(selectedDate)
    var hasDate = false

    override fun initLayout(view: View) {
        super.initLayout(view)
        getViewModel().getLists()


        getViewModel().reminderList.observe(viewLifecycleOwner, Observer {
            if (Utility.isNotNullOrEmpty(it)) {
                var passedList: ItemList? = null
                if (arguments?.getSerializable(BundleKey.SELECTED_LIST_ITEM.name) != null) {
                    passedList =
                        arguments?.getSerializable(BundleKey.SELECTED_LIST_ITEM.name) as ItemList
                }
                if (Utility.isNotNullOrEmpty(passedList)) {
                    handleSelectedList(passedList!!)
                } else
                    handleSelectedList(it[0])
                getViewModel().reminderList.postValue(null)
            }
        }
        )

        getViewBinding().viewList.setOnClickListener {
            showDialog(getViewModel().list.toTypedArray())
        }

        getViewBinding().viewSwitch.setOnCheckedChangeListener { compoundButton, chacked ->
            hasDate = chacked
            if (chacked) {
                showDatePickerDialog()
            } else {
                getViewBinding().dateTitle.gone()
            }
        }

        getViewBinding().dateTitle.setOnClickListener {
            showDatePickerDialog()
        }

        getViewModel().navigateBack.observe(viewLifecycleOwner, Observer {
            if (it) {
                getViewModel().showLoading(false)
                goBackAndRecreate()
                getViewModel().navigateBack.postValue(false)
            }
        })

    }

    private fun showDialog(items: Array<ItemList>) {
        val dialog = ChooseListDialogFragment.newInstance(items)
        dialog.listener = object : ChooseListDialogFragment.ChooseListDialogFragmentListener {
            override fun itemClicked(item: ItemList) {
                handleSelectedList(item)
                dialog.dismiss()
            }

            override fun addClicked() {
                navigationTo(R.id.action_addReminderFragment_to_addListFragment)
            }
        }
        dialog.show(childFragmentManager, "ChooseList")
    }

    private fun handleSelectedList(item: ItemList) {
        selectedList = item
        getViewBinding().bullet.setBackgroundColor(
            requireActivity().getColor(
                ReminderListColor.getColorByName(item.color).darkColor
            )
        )
        getViewBinding().txtList.text = item.title
    }

    override fun initToolbar() {

        setToolbarColor(R.color.colorPrimary)

        setToolbarTitle("Add Reminder")
        setToolbarTextColor(R.color.textColorBold)

        handleToolbarBackIconVisibility(true)
        setToolbarBackIconColor(R.color.textColorBold)

        setToolbarActionTitle("Add")
        handleToolbarActionVisibility(true)
        setToolbarActionOnclick {
            if (Utility.isNotNullOrEmpty(getViewBinding().txtTitle.text.toString())) {
                getViewModel().showLoading(true)
                getViewModel().insertItem(getItem())
            } else
                Toast.makeText(activity, "Complete Title Field", Toast.LENGTH_LONG).show()
        }

        handleToolbarImgVisibility(false)


    }

    private fun getItem() = Item(
        listId = selectedList.id,
        date = if (hasDate) selectedDate else null
    ).apply {
        this.isDone = false
        this.title =
            if (Utility.isNullOrEmpty(getViewBinding().txtTitle.text.toString())) "Title" else getViewBinding().txtTitle.text.toString()
        this.notes =
            if (Utility.isNullOrEmpty(getViewBinding().txtNote.text.toString())) "Notes" else getViewBinding().txtNote.text.toString()
    }

    private fun showDatePickerDialog() {
        val dialog = DatePickerFragment.newInstance(selectedDate)
        dialog.callBack = object : DatePickerFragment.CallBack {
            override fun onDateSelected(date: Date) {
                selectedDate = date
                selectedDateTitle = Utility.dateToString(selectedDate)
                getViewBinding().viewSwitch.isChecked = true
                getViewBinding().dateTitle.show()
                getViewBinding().dateTitle.text = selectedDateTitle
            }

            override fun onCancel() {
                hasDate = false
                getViewBinding().viewSwitch.isChecked = false
                getViewBinding().dateTitle.gone()
            }
        }
        dialog.show(childFragmentManager, "DatePicker")
    }
}
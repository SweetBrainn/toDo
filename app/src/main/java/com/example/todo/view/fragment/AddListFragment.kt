package com.example.todo.view.fragment

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.databinding.FragmentAddListBinding
import com.example.todo.model.Utility.Utility
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.enums.ReminderListColor
import com.example.todo.view.adapters.ListColorAdapter
import com.example.todo.viewModel.AddListViewModel


class AddListFragment : BaseFragment<FragmentAddListBinding, AddListViewModel>(
    R.layout.fragment_add_list,
    AddListViewModel::class.java
) {

    private val adapter = ListColorAdapter()
    private val colors = ReminderListColor.values().toMutableList()
    private lateinit var selectedColor: ReminderListColor
    override fun initLayout(view: View) {
        super.initLayout(view)
        initView()
        getViewModel().navigateBack.observe(viewLifecycleOwner, Observer {
            if (it) {
                goBackAndRecreate()
                getViewModel().navigateBack.postValue(false)
            }
        })

    }

    private fun initView() {
        handleSelectedItem(colors[0])
        initRecyclerView()
    }

    override fun initToolbar() {

        handleToolbarBackIconVisibility(true)
        handleToolbarBackIconVisibility(true)
        setToolbarBackIconColor(R.color.textColorBold)

        handleToolbarImgVisibility(false)

        handleToolbarActionVisibility(true)
        setToolbarActionTitle("Done")

        setToolbarTitle("Add List")
        setToolbarTextColor(R.color.textColorBold)


        setToolbarActionOnclick {
            if (Utility.isNotNullOrEmpty(getViewBinding().editTextListTitle.text.toString())) {
                getViewModel().showLoading(true)
                getViewModel().insertReminderList(
                    getList()
                )
            } else {
                Toast.makeText(activity, "Complete Title Field", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun getList() = ItemList(
        color = selectedColor.name,
        title = getViewBinding().editTextListTitle.text.toString()
    )

    private fun initRecyclerView() {
        getViewBinding().gridList.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        adapter.submitList(colors)
        getViewBinding().gridList.adapter = adapter
        adapter.onClickListener = object : ListColorAdapter.ListColorOnclickListener {
            override fun onclick(item: ReminderListColor) {
                handleSelectedItem(item)
            }
        }
    }

    private fun handleSelectedItem(item: ReminderListColor) {
        selectedColor = item
        try {
            val transitionDrawable =
                TransitionDrawable(
                    arrayOf(
                        ColorDrawable(requireContext().getColor(R.color.defaultBackgroundColor)),
                        ColorDrawable(requireContext().getColor(item.darkColor))
                    )
                )
            getViewBinding().selectedListView.background = transitionDrawable
            transitionDrawable.startTransition(900)
        } catch (ignore: Exception) {
        }
    }

}
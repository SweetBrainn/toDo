package com.example.todo.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.todo.R
import com.example.todo.databinding.DialogChooseListBinding
import com.example.todo.model.Utility.handleVisibility
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.enums.BundleKey
import com.example.todo.view.adapters.ChooseListAdapter

class ChooseListDialogFragment : DialogFragment() {

    private lateinit var viewBinding: DialogChooseListBinding
    private lateinit var adapter: ChooseListAdapter
    private lateinit var items: Array<ItemList>
    var listener: ChooseListDialogFragmentListener? = null

    companion object {
        fun newInstance(
            items: Array<ItemList>
        ): ChooseListDialogFragment {
            var bundle = Bundle().apply {
                putSerializable(BundleKey.LIST_ITEMS.name, items)
            }
            return ChooseListDialogFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        items = arguments?.getSerializable(BundleKey.LIST_ITEMS.name) as Array<ItemList>
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogFragmentTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_choose_list, container, false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun handleListVisibility(visible: Boolean) {
        viewBinding.list.handleVisibility(visible)
        viewBinding.viewAdd.handleVisibility(!visible)
    }

    private fun initView() {
        adapter = ChooseListAdapter()
        adapter.submitList(items.toMutableList())
        adapter.onClickListener = object : ChooseListAdapter.OnItemClickListener {
            override fun onClick(item: ItemList) {
                listener?.itemClicked(item)
            }
        }
        viewBinding.list.adapter = adapter
        viewBinding.viewAdd.setOnClickListener {
            dismiss()
            listener?.addClicked()
        }
        viewBinding.txtCancel.setOnClickListener {
            dismiss()
        }
        handleListVisibility(items.isNotEmpty())
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.dialogAnimation)
    }

    interface ChooseListDialogFragmentListener {
        fun addClicked()
        fun itemClicked(item: ItemList)
    }

}
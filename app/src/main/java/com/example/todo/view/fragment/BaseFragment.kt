package com.example.todo.view.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.navigation.fragment.findNavController
import com.example.todo.model.Utility.gone
import com.example.todo.model.Utility.handleVisibility
import com.example.todo.model.Utility.invisible
import com.example.todo.model.Utility.show
import com.example.todo.viewModel.BaseViewModel
import com.example.todo.view.activity.MainActivity
import com.example.todo.view.dialogs.Loading
import java.lang.Exception
import java.lang.IllegalStateException

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    private val view: Int,
    private val viewModelClass: Class<VM>
) :
    Fragment() {

    private val dialog: Loading? = Loading.newInstance()
    private var viewBinding: VB? = null
    private lateinit var viewModel: VM

    protected fun getViewBinding(): VB {
        return viewBinding ?: throw IllegalStateException("ViewBinding is null")
    }

    protected fun getViewModel(): VM {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
        viewBinding = DataBindingUtil.inflate(inflater, view, container, false)
        getViewBinding().setVariable(BR.viewModel, viewModel)
        getViewBinding().executePendingBindings()
        getViewBinding().setLifecycleOwner { lifecycle }
        return viewBinding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout(view)
        showToolbar()
        initToolbar()
    }

    open fun onViewCreated() {}

    open fun initLayout(view: View) {

        getViewModel().showLoadingLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null)
                showLoading(it)
        })

    }

    protected fun navigationTo(action: Int, bundle: Bundle? = null) {
        if (bundle == null)
            findNavController().navigate(action)
        else
            findNavController().navigate(action, bundle)
    }

    protected fun navigateBack() {
        findNavController().navigateUp()
    }

    private fun getHostActivity(): MainActivity = activity as MainActivity

    protected fun setToolbarColor(@ColorRes color: Int) {
        try {
            getHostActivity().getBindingView().toolbar.setBackgroundColor(
                requireActivity().getColor(
                    color
                )
            )
        } catch (ignore: Exception) {

        }
    }

    protected fun setToolbarTextColor(@ColorRes color: Int) {
        try {
            getHostActivity().getBindingView().txtToolbar.setTextColor(
                requireActivity().getColor(
                    color
                )
            )
        } catch (ignore: Exception) {

        }
    }

    protected fun setToolbarTitle(title: String) {
        getHostActivity().getBindingView().txtToolbar.text = title
    }

    protected fun setToolbarActionTitle(title: String) {
        getHostActivity().getBindingView().txtAction.text = title
    }

    protected fun handleToolbarImgVisibility(visible: Boolean) {
        getHostActivity().getBindingView().imgToolbar.invisible(visible)
    }

    protected fun setToolbarBackIconColor(color: Int) {
        getHostActivity().getBindingView().icBack.setColorFilter(
            ContextCompat.getColor(
                requireActivity(),
                color
            ), android.graphics.PorterDuff.Mode.SRC_IN
        );
    }

    protected fun handleToolbarTitleVisibility(visible: Boolean) {
        getHostActivity().getBindingView().txtToolbar.handleVisibility(visible)
    }

    protected fun handleToolbarBackIconVisibility(visible: Boolean) {
        getHostActivity().getBindingView().icBack.handleVisibility(visible)
    }

    protected fun handleToolbarActionVisibility(visible: Boolean) {
        getHostActivity().getBindingView().txtAction.handleVisibility(visible)
    }

    protected fun setToolbarImgIcon(@DrawableRes image: Int) {
        getHostActivity().getBindingView().imgToolbar.setBackgroundResource(image)
    }

    protected fun setToolbarActionOnclick(listener: View.OnClickListener) {
        getHostActivity().getBindingView().txtAction.setOnClickListener(listener)
    }

    protected fun showToolbar() {
        getHostActivity().getBindingView().toolbar.show()
    }

    protected fun hideToolbar() {
        getHostActivity().getBindingView().toolbar.gone()
    }

    abstract fun initToolbar()

    protected fun goBackAndRecreate() {
        getHostActivity().onBackPressed()
    }

    private fun showLoading(show: Boolean) {
        try {
            if (show) dialog?.show(parentFragmentManager, "loading") else {
                Handler().postDelayed({
                    dialog?.dismiss()
                }, 500)
            }
        } catch (e: Exception) {
        }
    }

}
package com.example.todo.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.todo.R
import com.example.todo.databinding.DialogChooseListBinding
import com.example.todo.databinding.LoadingBinding

class Loading : DialogFragment() {

    private lateinit var viewBinding: LoadingBinding

    companion object {
        fun newInstance(

        ): Loading {

            return Loading().apply {
                arguments = Bundle()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogFragmentTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.loading, container, false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(context?.getDrawable(R.color.loadingBackground))
        return viewBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewBinding.viewLoading.startAnimation(
            AnimationUtils.loadAnimation(context, R.anim.loading_fade)
        )
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.dialogAnimation)
    }

}
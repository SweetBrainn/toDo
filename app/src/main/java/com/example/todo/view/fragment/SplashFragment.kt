package com.example.todo.view.fragment

import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.example.todo.R
import com.example.todo.databinding.FragmentSplashBinding
import com.example.todo.viewModel.SplashViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    R.layout.fragment_splash,
    SplashViewModel::class.java
) {

    override fun initLayout(view: View) {
        super.initLayout(view)
        getViewModel().getLists()

        getViewBinding().icon.startAnimation(
            AnimationUtils.loadAnimation(activity, R.anim.side_slide)
        )

        getViewModel().startApplication.observe(viewLifecycleOwner, Observer {
            if (it) {
                getViewBinding().appLabel.startAnimation(
                    AnimationUtils.loadAnimation(activity, R.anim.fade).apply {
                        startOffset = 1000
                    }
                )
                Handler().postDelayed({
                    navigationTo(R.id.action_splashFragment_to_homeFragment)
                }, 4000)
                getViewModel().startApplication.postValue(false)
            }
        })

    }

    override fun initToolbar() {
        hideToolbar()
    }

}
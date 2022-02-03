package com.example.todo.view.activity


import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.view.fragment.HomeFragment


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onActivityCreated() {
        super.onActivityCreated()
        initToolbar()
    }

    private fun initToolbar() {
        setApplicationLabel()
        viewBinding.icBack.setOnClickListener { onBackPressed() }
    }

    private fun setApplicationLabel() {
        viewBinding.txtToolbar.text = "ToDo"

    }

    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (navHostFragment!!.childFragmentManager.fragments[0] is HomeFragment
        ) {
            finishAffinity()
        }
        else {
            navHostFragment.findNavController().navigateUp()
        }
    }

    fun getBindingView() = viewBinding

}
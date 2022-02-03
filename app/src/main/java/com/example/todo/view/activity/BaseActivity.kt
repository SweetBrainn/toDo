package com.example.todo.view.activity

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding

open class BaseActivity<VB : ViewDataBinding>(@LayoutRes private val view: Int) :
    AppCompatActivity(){

    protected lateinit var viewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, view)
        onActivityCreated()
    }

    open protected fun onActivityCreated() {

    }


}
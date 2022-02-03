package com.example.todo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val showLoadingLiveData = MutableLiveData<Boolean>(null)

    fun showLoading(show: Boolean) {
        showLoadingLiveData.postValue(show)
    }

}



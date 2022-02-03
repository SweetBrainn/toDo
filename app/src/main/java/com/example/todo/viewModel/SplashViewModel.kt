package com.example.todo.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.todo.model.Utility.Utility
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.enums.ReminderListColor
import com.example.todo.repository.ItemListRepository
import io.reactivex.rxjava3.functions.Consumer

class SplashViewModel : BaseViewModel() {

    var itemListRepository = ItemListRepository.get()
    var startApplication = MutableLiveData<Boolean>(false)

    fun getLists() {
        itemListRepository.getItemLists().subscribe(Consumer {
            if (Utility.isNullOrEmpty(it)) {
                insertDefaultLists()
            } else {
                startApplication.postValue(true)
            }
        })
    }

    private fun insertDefaultLists() {
        itemListRepository.insertItemList(
            ItemList(
                title = "Default List",
                color = ReminderListColor.DEFAULT.name
            )
        ).subscribe(Consumer {
            startApplication.postValue(true)
        })
    }

}
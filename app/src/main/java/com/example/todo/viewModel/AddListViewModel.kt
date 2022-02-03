package com.example.todo.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.todo.model.database.entity.ItemList
import com.example.todo.repository.ItemListRepository
import io.reactivex.rxjava3.functions.Consumer

class AddListViewModel : BaseViewModel() {

    private val listRepository = ItemListRepository.get()
    val navigateBack = MutableLiveData<Boolean>(false)

    fun insertReminderList(reminderList: ItemList) {
        listRepository.insertItemList(reminderList).subscribe(Consumer {
            showLoading(false)
            navigateBack.postValue(true)
        })
    }

}
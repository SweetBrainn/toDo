package com.example.todo.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.todo.model.database.entity.Item
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.enums.ReminderListColor
import com.example.todo.repository.ItemListRepository
import com.example.todo.repository.ItemRepository
import io.reactivex.rxjava3.functions.Consumer

class AddReminderViewModel : BaseViewModel() {

    val reminderList = MutableLiveData<List<ItemList>>()
    var listRepository = ItemListRepository.get()
    var itemRepository = ItemRepository.get()
    var navigateBack = MutableLiveData<Boolean>(false)

    var list = listOf<ItemList>()

    fun getLists() {
        listRepository.getItemLists().subscribe(Consumer {
            list = it
            reminderList.postValue(it)
        })
    }

    fun insertItem(item: Item) = itemRepository.insertItem(item).subscribe(
        Consumer {
            navigateBack.postValue(true)
        }
    )

}
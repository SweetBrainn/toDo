package com.example.todo.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.todo.model.database.entity.Item
import com.example.todo.model.enums.CustomReminderType
import com.example.todo.repository.ItemRepository
import io.reactivex.rxjava3.functions.Consumer

class TypedReminderViewModel : BaseViewModel() {

    private val itemRepository = ItemRepository.get()

    var toolbarTitle: String = ""
    var toolbarBackgroundColor: Int = 0
    var toolbarTextColor: Int = 0
    var toolbarIcon: Int = 0
    var customReminderType: CustomReminderType = CustomReminderType.DEFAULT
    var reminders = mutableListOf<Item>()
    var listVisibility = MutableLiveData<Boolean>(false)
    var remindersFromDB = MutableLiveData<List<Item>>()

    fun getItems() {
        itemRepository.getCustomItems(customReminderType).subscribe(Consumer {
            reminders = it.toMutableList()
            remindersFromDB.postValue(it)
        })
    }

    fun updateItem(item: Item) = itemRepository.updateItem(item).subscribe(Consumer {})

}
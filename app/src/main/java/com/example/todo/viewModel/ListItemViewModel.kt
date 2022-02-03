package com.example.todo.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.todo.model.database.entity.Item
import com.example.todo.model.database.entity.ListWithItems
import com.example.todo.repository.ItemListRepository
import com.example.todo.repository.ItemRepository
import io.reactivex.rxjava3.functions.Consumer

class ListItemViewModel : BaseViewModel() {

    val itemRepository = ItemRepository.get()
    val itemListRepository = ItemListRepository.get()

    lateinit var passedData: ListWithItems
    var dataTitle = MutableLiveData<String>()
    var remindersCount = MutableLiveData<Int>()
    var listVisibility = MutableLiveData<Boolean>(false)
    var remindersLiveData = MutableLiveData<MutableList<Item>>()


    fun updateItems(items: Item) = itemRepository.updateItem(items).subscribe(Consumer {

    })

    fun initData(input: ListWithItems) {
        passedData = input
        dataTitle.postValue(passedData.list.title)
        itemListRepository.getItemsByList(passedData.list.color).subscribe(Consumer {
            val items = getItems(it)
            remindersCount.postValue(items.size)
            remindersLiveData.postValue(items.toMutableList())
            listVisibility.postValue(items.isNotEmpty())
        })
    }


    private fun getItems(data: List<ListWithItems>): MutableList<Item> {
        val temp = mutableListOf<Item>()
        data.forEach { listWithItems ->
            temp.addAll(listWithItems.items)
        }
        return temp
    }


}


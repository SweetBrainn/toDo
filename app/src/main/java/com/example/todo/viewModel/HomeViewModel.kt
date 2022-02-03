package com.example.todo.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.todo.model.Utility.Utility
import com.example.todo.model.database.entity.Item
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.database.entity.ListWithItems
import com.example.todo.repository.ItemListRepository
import io.reactivex.rxjava3.functions.Consumer
import java.util.*
import kotlin.collections.HashMap

class HomeViewModel : BaseViewModel() {

    private val itemListRepository = ItemListRepository.get()

    var listFromDB = MutableLiveData<List<ListWithItems>>()
    var todayRemindersCount = MutableLiveData<Int>(0)
    var allRemindersCount = MutableLiveData<Int>(0)
    var doneRemindersCount = MutableLiveData<Int>(0)
    var scheduledRemindersCount = MutableLiveData<Int>(0)
    var toDoRemindersCount = MutableLiveData<Int>(0)

    var allData: List<ListWithItems> = listOf()
    private var allReminders: List<Item> = listOf()

    var todayReminders: HashMap<UUID, Item> = hashMapOf()
    var scheduleReminders: HashMap<UUID, Item> = hashMapOf()
    var doneReminders: HashMap<UUID, Item> = hashMapOf()
    var toDoReminders: HashMap<UUID, Item> = hashMapOf()

    fun getAllData() {
        itemListRepository.getListWithItems().subscribe(Consumer {
            allData = it
            listFromDB.postValue(it)
            getAllReminders()
            getTodayReminders()
            getDoneReminders()
            getSchedulesReminders()
        })
    }

    private fun getSchedulesReminders() {
        allReminders.forEach { reminder ->
            if (reminder.date != null) scheduleReminders[reminder.id] =
                reminder
        }
        scheduledRemindersCount.postValue(scheduleReminders.size)
    }

//    private fun getDateFormat(date: Date?): String {
//        date?.let {
//            val calendar = Calendar.getInstance()
//            calendar.timeInMillis = Date().time
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//            return "$year/$month/$day"
//        }
//        return ""
//    }

    private fun getTodayReminders() {
        val today = Utility.dateToString(Date())
        allReminders.forEach { reminder ->
            val date = Utility.dateToString(reminder.date)
            if (date == today) todayReminders[reminder.id] =
                reminder
        }
        todayRemindersCount.postValue(todayReminders.size)
    }

    private fun getDoneReminders() {
        allReminders.forEach { reminder ->
            if (reminder.isDone) doneReminders[reminder.id] =
                reminder else toDoReminders[reminder.id] = reminder
        }
        toDoRemindersCount.postValue(toDoReminders.size)
        doneRemindersCount.postValue(doneReminders.size)
    }

    private fun getAllReminders() {
        val temp = arrayListOf<Item>()
        allData.forEach { list ->
            list.items.forEach { reminder ->
                temp.add(reminder)
            }
        }
        allReminders = temp
        allRemindersCount.postValue(allReminders.size)
    }


    fun resetViewModel() {
        listFromDB = MutableLiveData<List<ListWithItems>>()
        todayRemindersCount = MutableLiveData<Int>(0)
        allRemindersCount = MutableLiveData<Int>(0)
        doneRemindersCount = MutableLiveData<Int>(0)
        scheduledRemindersCount = MutableLiveData<Int>(0)
        toDoRemindersCount = MutableLiveData<Int>(0)

        allData = listOf()
        allReminders = listOf()

        todayReminders = hashMapOf()
        scheduleReminders = hashMapOf()
        doneReminders = hashMapOf()
        toDoReminders = hashMapOf()
    }

}

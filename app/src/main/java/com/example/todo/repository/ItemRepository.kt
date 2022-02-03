package com.example.todo.repository

import android.content.Context
import androidx.room.Room
import com.example.todo.model.Utility.Utility
import com.example.todo.model.database.db.ReminderDatabase
import com.example.todo.model.database.entity.Item
import com.example.todo.model.enums.CustomReminderType
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.ComputationScheduler
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "reminder-database"

class ItemRepository private constructor(context: Context) {
    private val executor = Executors.newSingleThreadExecutor()


    companion object {
        private var INSTANCE: ItemRepository? = null

        fun init(context: Context) {
            if (INSTANCE == null)
                INSTANCE = ItemRepository(context)
        }

        fun get(): ItemRepository {
            return INSTANCE ?: throw IllegalStateException("ItemRepository must be initialized")
        }
    }

    private val database: ReminderDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            ReminderDatabase::class.java,
            DATABASE_NAME
        )
            .build()

    private val itemDao = database.itemDao()

    fun insertItem(item: Item): Single<Long> =
        itemDao.insertItem(item).subscribeOn(ComputationScheduler())

    fun getCustomItems(type: CustomReminderType = CustomReminderType.DEFAULT): Maybe<List<Item>> {
        val method = when (type) {
            CustomReminderType.TODAY -> itemDao.getTodayItems(Utility.dateToString(Date())!!)
            CustomReminderType.DONE -> itemDao.getDoneItems()
            CustomReminderType.TODO -> itemDao.getTodoItems()
            CustomReminderType.SCHEDULED -> itemDao.getScheduleItems()
            else -> itemDao.getItems()
        }
        return method.subscribeOn(ComputationScheduler())
    }

    fun updateItem(item: Item) = itemDao.updateItem(item).subscribeOn(ComputationScheduler())


}
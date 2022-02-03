package com.example.todo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todo.model.database.db.ReminderDatabase
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.database.entity.ListWithItems
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.ComputationScheduler
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "reminder-database"

class ItemListRepository private constructor(context: Context) {


    companion object {
        private var INSTANCE: ItemListRepository? = null

        fun init(context: Context) {
            if (INSTANCE == null)
                INSTANCE = ItemListRepository(context)
        }

        fun get(): ItemListRepository {
            return INSTANCE ?: throw IllegalStateException("ItemListRepository must be initialized")
        }
    }

    private val database: ReminderDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            ReminderDatabase::class.java,
            DATABASE_NAME
        )
            .build()

    private val itemListDao = database.itemListDao()

    fun insertItemList(item: ItemList) =
        itemListDao.insertItemList(item).subscribeOn(ComputationScheduler())

    fun getItemLists() = itemListDao.getItemLists().subscribeOn(ComputationScheduler())


    fun getListWithItems() =
        itemListDao.getListWithItems().subscribeOn(ComputationScheduler())

    fun getItemsByList(color: String) =
        itemListDao.getItemsByList(color).subscribeOn(ComputationScheduler())

}
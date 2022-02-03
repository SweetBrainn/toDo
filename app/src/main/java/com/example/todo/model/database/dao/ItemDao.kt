package com.example.todo.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.model.database.entity.Item
import com.example.todo.model.database.entity.ItemList
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface ItemDao {

    @Query("SELECT * FROM Item")
    fun getItems(): Maybe<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item):Single<Long>

    @Query("SELECT * FROM Item WHERE date = (:today) ")
    fun getTodayItems(today: String): Maybe<List<Item>>

    @Query("SELECT * FROM Item WHERE isDone = 1 ")
    fun getDoneItems(): Maybe<List<Item>>

    @Query("SELECT * FROM Item WHERE isDone = 0 ")
    fun getTodoItems(): Maybe<List<Item>>

    @Query("SELECT * FROM Item WHERE date != 0 ")
    fun getScheduleItems(): Maybe<List<Item>>

    @Update
    fun updateItem(item: Item):Single<Int>

}
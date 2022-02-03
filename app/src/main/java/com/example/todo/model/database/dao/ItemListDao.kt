package com.example.todo.model.database.dao

import androidx.room.*
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.database.entity.ListWithItems
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface ItemListDao {

    @Query("SELECT * FROM ItemList")
    fun getItemLists(): Maybe<List<ItemList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemList(itemList: ItemList): Single<Long>

    @Transaction
    @Query("SELECT * FROM ItemList")
    fun getListWithItems(): Maybe<List<ListWithItems>>

    @Update
    fun updateList(item: ItemList): Single<Int>

    @Transaction
    @Query("SELECT * FROM ItemList WHERE color = :color ")
    fun getItemsByList(color: String): Maybe<List<ListWithItems>>

}
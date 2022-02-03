package com.example.todo.model.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.todo.model.database.dao.ItemDao
import com.example.todo.model.database.dao.ItemListDao
import com.example.todo.model.database.entity.Item
import com.example.todo.model.database.entity.ItemList
import com.example.todo.model.database.typeConverter.MyTypeConverter

@Database(entities = [ItemList::class, Item::class], version = 1)
@TypeConverters(MyTypeConverter::class)
abstract class ReminderDatabase : RoomDatabase() {

    abstract fun itemListDao(): ItemListDao
    abstract fun itemDao(): ItemDao
}
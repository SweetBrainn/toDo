package com.example.todo.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.model.enums.ReminderListColor
import java.io.Serializable
import java.util.*

@Entity
data class ItemList(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "DEFAULT",
    var color: String = ReminderListColor.DEFAULT.name
):Serializable
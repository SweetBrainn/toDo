package com.example.todo.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Item(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var listId: UUID,
    var title: String = "DEFAULT",
    var notes: String? = "",
    var isDone: Boolean = false,
    var date: Date? = null,
    val createdDate: Date = Date()
) : Serializable
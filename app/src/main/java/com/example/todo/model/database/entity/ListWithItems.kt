package com.example.todo.model.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class ListWithItems(
    @Embedded val list: ItemList,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId"
    )
    var items: List<Item>,
) : Serializable
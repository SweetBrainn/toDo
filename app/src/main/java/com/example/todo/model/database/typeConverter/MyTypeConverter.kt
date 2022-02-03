package com.example.todo.model.database.typeConverter

import androidx.room.TypeConverter
import com.example.todo.model.Utility.Utility
import java.util.*

class MyTypeConverter {

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return Utility.dateToString(date)
    }

    @TypeConverter
    fun toDate(date: String?): Date? {
        return Utility.stringToDate(date)
    }

    @TypeConverter
    fun toUUID(uuid: String): UUID {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

}
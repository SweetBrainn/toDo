package com.example.todo.model.Utility

import java.text.SimpleDateFormat
import java.util.*


object Utility {

    fun dateToString(date: Date?): String? {
        date?.let {

            val format = SimpleDateFormat("dd/MM/yyyy")
            return format.format(date)
//            var sdf = DateForma
//            val calendar = Calendar.getInstance()
//            calendar.timeInMillis = it.time
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//            return "$day-$month-$year"
        }

        return null

    }

    fun stringToDate(date: String?): Date? {
        date?.let {
            return SimpleDateFormat("dd/MM/yyyy").parse(date)
        }
        return null
    }

    fun getDayTitle(date: Date?): String {
        date?.let {
            return SimpleDateFormat("EEE").format(date).substring(0, 3)
        }
        return ""
    }

    fun getDay(date: Date?): String {
        date?.let {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.get(Calendar.DAY_OF_MONTH).toString()
        }
        return ""
    }

    fun isNullOrEmpty(input: Any?): Boolean {
        if (input == null) return true

        if (input is String) {
            return input == ""
        }

        if (input is Date) {
            return isNullOrEmpty(dateToString(input)!!)
        }

        if (input is List<*>) {
            return input.size == 0
        }

        return false

    }

    fun isNotNullOrEmpty(input: Any?): Boolean {
        return !isNullOrEmpty(input)
    }

    fun getMonthName(date: Date?): String {
        date?.let {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return getMonthName(calendar.get(Calendar.MONTH))
        }
        return ""
    }

    fun getMonthName(month: Int): String {
        return when (month) {
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "May"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Oct"
            10 -> "Nov"
            11 -> "Dec"
            else -> ""
        }
    }

}
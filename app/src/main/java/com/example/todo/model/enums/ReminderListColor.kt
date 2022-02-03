package com.example.todo.model.enums

import com.example.todo.R


enum class ReminderListColor(val lightColor: Int, val darkColor: Int) {

    BLUE(R.color.blue_light, R.color.blue_dark),
    RED(R.color.red_light, R.color.red_dark),
    ORANGE(R.color.orange_light, R.color.orange_dark),
    PURPLE(R.color.purple_light, R.color.purple_dark),
    GREEN(R.color.green_light, R.color.green_dark),
    PINK(R.color.yellow_light, R.color.yellow_dark),
    DEFAULT(R.color.default_light, R.color.default_dark);

    companion object {

        fun getColorByName(name: String): ReminderListColor {
            values().forEach {
                if (it.name.equals(name, true)) return it
            }
            return DEFAULT
        }

    }

}
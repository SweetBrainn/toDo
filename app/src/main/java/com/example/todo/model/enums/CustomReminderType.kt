package com.example.todo.model.enums

import java.io.Serializable

enum class CustomReminderType() : Serializable {

    TODAY,
    DEFAULT,
    DONE,
    SCHEDULED,
    TODO

}
package com.example.todo

import android.app.Application
import com.example.todo.repository.ItemListRepository
import com.example.todo.repository.ItemRepository

public class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ItemListRepository.init(this.applicationContext)
        ItemRepository.init(this.applicationContext)
    }

}
package com.example.kholidays

import android.app.Application
import com.example.kholidays.di.AppComponent
import com.example.kholidays.di.DaggerAppComponent

class App : Application() {
        val component: AppComponent by lazy { DaggerAppComponent.create() }
}
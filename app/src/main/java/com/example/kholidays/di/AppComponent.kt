package com.example.kholidays.di

import com.example.centerofcat.data.repositories.HolidaysRepositoryImpl
import com.example.kaiholidays.di.ApiFactoryModule
import com.example.kaiholidays.di.ApiServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiFactoryModule::class, ApiServiceModule::class])
interface AppComponent {
    fun injectHolidaysRepository(holidaysRepository: HolidaysRepositoryImpl)
}
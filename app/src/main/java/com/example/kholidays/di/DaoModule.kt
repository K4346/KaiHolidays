//package com.example.kholidays.di
//
//import com.example.kholidays.App
//import com.example.kholidays.data.bd.HolidaysDao
//import com.example.kholidays.data.bd.HolidaysDatabase
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//@Module
//class DaoModule {
//    @Provides
//    @Singleaton
//    fun provideDao(application: App): HolidaysDao {
//        return HolidaysDatabase.getInstance(application.applicationContext).holidaysDao()
//    }
//}
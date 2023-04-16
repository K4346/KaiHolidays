package com.example.kholidays.domain.repositories

import android.content.Context
import com.example.kholidays.domain.entities.DayInfo
import io.reactivex.Single

interface HolidaysRepository {

    fun getHolidaysFromNet(
        year: String,
        month: String,
        day: String
    ): Single<List<DayInfo>>

    fun getAllHolidaysFromBd(context: Context): Single<List<DayInfo>>


    fun insertDayInBd(context: Context,day:DayInfo): Unit

    fun updateDayInBd(context: Context,day:DayInfo): Unit

    fun deleteDayInBd(context: Context,day:DayInfo): Unit
}
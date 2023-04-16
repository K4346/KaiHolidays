package com.example.centerofcat.data.repositories

import android.content.Context
import com.example.kholidays.App
import com.example.kholidays.data.api.services.ApiService
import com.example.kholidays.data.bd.HolidaysDatabase
import com.example.kholidays.domain.repositories.HolidaysRepository
import com.example.kholidays.domain.entities.DayInfo
import io.reactivex.Single
import javax.inject.Inject

class HolidaysRepositoryImpl : HolidaysRepository {
    @Inject
    lateinit var apiService: ApiService

    private val app= App()

    override fun getHolidaysFromNet(
        year: String,
        month: String,
        day: String
    ): Single<List<DayInfo>> {
        app.component.injectHolidaysRepository(this)
        return apiService.getHoliday(year=year, month=month, day=day)
    }

    override fun getAllHolidaysFromBd(context: Context): Single<List<DayInfo>> {
        val bd = HolidaysDatabase.getInstance(context).holidaysDao()
        return bd.getAll()
    }

    override fun insertDayInBd(context: Context,day: DayInfo) {
        val bd = HolidaysDatabase.getInstance(context).holidaysDao()
        return bd.insert(day)
    }

    override fun updateDayInBd(context: Context,day:DayInfo): Unit {
        val bd = HolidaysDatabase.getInstance(context).holidaysDao()
        return bd.update(day)
    }

    override fun deleteDayInBd(context: Context,day:DayInfo): Unit {
        val bd = HolidaysDatabase.getInstance(context).holidaysDao()
        return bd.delete(day)
    }
}
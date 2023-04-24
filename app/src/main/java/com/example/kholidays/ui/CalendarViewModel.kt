package com.example.kholidays.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.centerofcat.data.repositories.HolidaysRepositoryImpl
import com.example.kholidays.SingleLiveEvent
import com.example.kholidays.domain.entities.DayInfo
import com.example.kholidays.domain.repositories.HolidaysRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CalendarViewModel(private val application: Application) : AndroidViewModel(application) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val holidaysRepository: HolidaysRepository = HolidaysRepositoryImpl()
    val holidaysSLE: SingleLiveEvent<String> = SingleLiveEvent()
    private var days: MutableList<DayInfo> = arrayListOf()

    fun loadHolidays(
        year: Int = 2023,
        month: Int = 1,
        day: Int = 1,
    ) {
        Log.i("kpop", "1")

        val disposable = holidaysRepository.getAllHolidaysFromBd(application.applicationContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                days = it as MutableList<DayInfo>
                getDay(year, month, day)
            }, {
                Log.i("kpop", it.toString())
            }
            )
        compositeDisposable.add(disposable)
    }

    private fun getDay(year: Int, month: Int, day: Int) {
        val d = days.filter { it.date_year == year && it.date_month == month && it.date_day == day }
        if (d.isNotEmpty()) {
            holidaysSLE.value = getHolidaysFromDay(d)
        } else {
            loadDayFromNet(year, month, day)
        }
    }

    private fun loadDayFromNet(year: Int, month: Int, day: Int) {
        val disposable = holidaysRepository.getHolidaysFromNet(
            year = year.toString(),
            month = month.toString(),
            day = day.toString()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.forEach { holiday ->
                    insertDayToBd(holiday)
                }
                if (it.isEmpty()) {
                    val holiday = DayInfo("Обычный день", year, month, day)
                    insertDayToBd(holiday)
                    holidaysSLE.value = getHolidaysFromDay(listOf(holiday))
                } else {
                    holidaysSLE.value = getHolidaysFromDay(it)
                }
            }, {
                Toast.makeText(application.applicationContext,"Интернет соединение отсутствует",Toast.LENGTH_LONG).show()
            }
            )
        compositeDisposable.add(disposable)
    }

    private fun getHolidaysFromDay(it: List<DayInfo>): String {
        var days = ""
        it.forEachIndexed { index, day ->
            if (0 == index) {
                days += day.name
            } else {
                days += " ${day.name}"
            }
            if (it.lastIndex != index) {
                days += ","
            }
        }
        return days
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        deletePastDays()
    }

    private fun insertDayToBd(holiday: DayInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            holidaysRepository.insertDayInBd(application.applicationContext, holiday)
        }
        days.add(holiday)
    }

    fun deletePastDays() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1
        val currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        days.forEach {
            Log.i("kpop","${it.date_year} ${it.date_month} ${it.date_day}<${currentYear} ${currentMonth} ${currentDayOfMonth}")
            if (it.date_year == null || it.date_month == null || it.date_day == null) {
                deleteDay(it)
            } else if (it.date_year < currentYear || it.date_month < currentMonth || it.date_day < currentDayOfMonth) {
                deleteDay(it)
            }
        }
    }

    private fun deleteDay(day: DayInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            holidaysRepository.deleteDayInBd(application.applicationContext, day)
        }
    }
}
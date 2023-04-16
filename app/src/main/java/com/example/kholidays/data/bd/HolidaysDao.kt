package com.example.kholidays.data.bd

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kholidays.domain.entities.DayInfo
import io.reactivex.Single


@Dao
interface HolidaysDao {
    @Query("SELECT * FROM holidays")
    fun getAll(): Single<List<DayInfo>>

    @Insert
    fun insert(day: DayInfo)

    @Update
    fun update(day: DayInfo)

    @Delete
    fun delete(day: DayInfo)
}
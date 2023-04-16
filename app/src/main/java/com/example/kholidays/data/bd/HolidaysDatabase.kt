package com.example.kholidays.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kholidays.domain.entities.DayInfo

@Database(entities = [DayInfo::class], version = 1)
abstract class HolidaysDatabase : RoomDatabase() {
    abstract fun holidaysDao(): HolidaysDao

    companion object {
        private var db: HolidaysDatabase? = null
        private const val DB_NAME = "holidays.db"
        private val Lock = Any()
        fun getInstance(context: Context): HolidaysDatabase {
            synchronized(Lock) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(context, HolidaysDatabase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }
}
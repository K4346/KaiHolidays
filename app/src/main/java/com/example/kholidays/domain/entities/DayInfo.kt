package com.example.kholidays.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "holidays")
data class DayInfo(
    val name:String?,
    val date_year:Int?,
    val date_month:Int?,
    val date_day:Int?,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

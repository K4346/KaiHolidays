package com.example.kholidays.data.api.services

import com.example.kholidays.domain.entities.DayInfo
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @GET(value = "/v1")
    fun getHoliday(
        @Query("api_key")  apiKey: String = "3645b158506c40fd9c0542e87c830ea2",
        @Query("country") country: String = "RU",
        @Query("year") year: String,
        @Query("month") month: String,
        @Query("day") day: String,
    ): Single<List<DayInfo>>


}
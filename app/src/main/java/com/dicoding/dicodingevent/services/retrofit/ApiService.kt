package com.dicoding.dicodingevent.services.retrofit

import com.dicoding.dicodingevent.services.response.AvailableResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getAvailableEvent(
        @Query("active") active: Int
    ): Call<AvailableResponse>
}

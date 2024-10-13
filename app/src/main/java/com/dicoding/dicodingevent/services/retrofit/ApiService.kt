package com.dicoding.dicodingevent.services.retrofit

import com.dicoding.dicodingevent.services.response.AvailableResponse
import com.dicoding.dicodingevent.services.response.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getAvailableEvent(
        @Query("active") active: Int
    ): Call<AvailableResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int
    ): Call<DetailResponse>
}

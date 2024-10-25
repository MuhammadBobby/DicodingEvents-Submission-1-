package com.dicoding.dicodingevent.services.retrofit

import com.dicoding.dicodingevent.services.response.AvailableResponse
import com.dicoding.dicodingevent.services.response.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Menggunakan Response untuk coroutine
    @GET("events")
    suspend fun getAvailableEvent(
        @Query("active") active: Int
    ): Response<AvailableResponse>

    @GET("events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: Int
    ): Response<DetailResponse>

    // Search event dengan suspend function
    @GET("events")
    suspend fun getSearchEvent(
        @Query("active") active: Int = -1,
        @Query("q") q: String? = null
    ): Response<AvailableResponse>
}

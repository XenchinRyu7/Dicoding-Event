package com.saefulrdevs.dicodingevent.data.retrofit

import com.saefulrdevs.dicodingevent.data.response.EventDetailResponse
import com.saefulrdevs.dicodingevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events?active=1")
    fun getAllActiveEvent(): Call<EventResponse>

    @GET("events?active=0")
    fun getAllFinishedEvent(): Call<EventResponse>

    @GET("events?active=-1&q={keyword}")
    fun searchEvent(
        @Path("keyword") keyword: String
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<EventDetailResponse>

}
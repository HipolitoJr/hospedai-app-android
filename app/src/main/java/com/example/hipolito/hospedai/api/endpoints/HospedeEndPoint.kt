package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.Hospede
import com.example.hipolito.hospedai.model.Hotel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface HospedeEndPoint {

    @GET("hoteis/{hotel_id}/hospedes/")
    fun getHospedes(@Path("hotel_id") hotelId: Long): Call<MutableList<Hospede>>

    @GET("hoteis/{hotel_id}/hospedes/")
    fun postHospedes(@Path("hotel_id") hotelId: Long, @Body hotel: Hotel): Call<Hospede>

}
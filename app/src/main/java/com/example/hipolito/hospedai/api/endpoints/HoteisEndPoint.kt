package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.Hotel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by hipolito on 03/03/18.
 */
interface HoteisEndPoint {

    @GET("hoteis/")
    fun getHoteis(): Call<MutableList<Hotel>>

    @GET("hoteis/{id}/")
    fun getHotel(@Path("id") id: Long): Call<Hotel>

    @POST("hoteis/")
    fun postHotel(@Body hotel: Hotel): Call<Hotel>

}
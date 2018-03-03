package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.Hotel
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by hipolito on 03/03/18.
 */
interface HoteisEndPoint {

    @GET("hoteis/")
    fun getHoteis(): Call<MutableList<Hotel>>

}
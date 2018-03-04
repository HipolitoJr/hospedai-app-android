package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.Hospedagem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by hipolito on 04/03/18.
 */
interface HospedagensEndPoint {

    @GET("hoteis/{hotel_id}/hospedagens/")
    fun getHospedagens(@Path("hotel_id") hotel_id: Long): Call<MutableList<Hospedagem>>

}
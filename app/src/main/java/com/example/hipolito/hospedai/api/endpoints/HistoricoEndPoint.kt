package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.Hospedagem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by hipolito on 07/03/18.
 */
interface HistoricoEndPoint {

    @GET("hoteis/{hotel_id}/historico")
    fun getHistorico(@Path("hotel_id") hotelId: Long): Call<MutableList<Hospedagem>>

}
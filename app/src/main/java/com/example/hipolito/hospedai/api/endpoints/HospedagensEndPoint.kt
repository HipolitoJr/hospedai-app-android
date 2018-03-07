package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.Hospedagem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by hipolito on 04/03/18.
 */
interface HospedagensEndPoint {

    @GET("hoteis/{hotel_id}/hospedagens/")
    fun getHospedagens(@Path("hotel_id") hotel_id: Long): Call<MutableList<Hospedagem>>

    @POST("hoteis/{hotel_id}/hospedar/{hospede_id}/")
    fun postHospedagens(@Path("hotel_id") hotel_id: Long, @Path("hospede_id") hospede_id: Long, @Body hospedagem: Hospedagem): Call<Hospedagem>

    @POST("hoteis/{hotel_id}/hospedagens/{hospedagem_id}/checkout/")
    fun checkoutHospedagem(@Path("hotel_id") hotel_id: Long, @Path("hospedagem_id") hospedagem_id: Long): Call<Hospedagem>

}
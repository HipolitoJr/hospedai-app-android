package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.TokenAPIModel
import com.example.hipolito.hospedai.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by hipolito on 27/02/18.
 */
interface LoginEndPoint {

    @POST("token/")
    fun loginAPI(@Body usuario: Usuario): Call<TokenAPIModel>

}
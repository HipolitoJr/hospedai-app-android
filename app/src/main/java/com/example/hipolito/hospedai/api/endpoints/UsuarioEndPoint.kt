package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by hipolito on 21/03/18.
 */
interface UsuarioEndPoint {

    @GET("usuarios/{id}/")
    fun getUsuario(@Path("id") id: Long): Call<Usuario>

}
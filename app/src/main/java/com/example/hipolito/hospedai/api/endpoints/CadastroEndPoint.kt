package com.example.hipolito.hospedai.api.endpoints

import com.example.hipolito.hospedai.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by hipolito on 02/03/18.
 */
interface CadastroEndPoint {

    @POST("usuarios/")
    fun cadastroUsuario(@Body usuario: Usuario): Call<Usuario>

}
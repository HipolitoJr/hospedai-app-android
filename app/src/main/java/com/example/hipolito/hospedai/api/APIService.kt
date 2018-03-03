package com.example.hipolito.hospedai.api

import com.example.hipolito.hospedai.api.endpoints.CadastroEndPoint
import com.example.hipolito.hospedai.api.endpoints.LoginEndPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by hipolito on 27/02/18.
 */

class APIService{

    private val BASE_URL = "http://10.0.20.66:8000/api/v1/"

    private lateinit var retrofit: Retrofit
    private lateinit var interceptorAPI: InterceptorAPI

    lateinit var loginEndPoint: LoginEndPoint
    lateinit var cadastroEndPoint: CadastroEndPoint

    constructor(TOKEN: String){

        interceptorAPI = InterceptorAPI("Token " + TOKEN)

        val builderCliente = OkHttpClient.Builder()
        builderCliente.addInterceptor(interceptorAPI)
        val cliente = builderCliente.build()

        val builderRetrofit = Retrofit.Builder()
        retrofit = builderRetrofit
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cliente)
                .build()

        loginEndPoint = this.retrofit.create(LoginEndPoint::class.java)
        cadastroEndPoint = this.retrofit.create(CadastroEndPoint::class.java)
    }
}
package com.example.hipolito.hospedai.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by hipolito on 27/02/18.
 */

class APIService{

    public val BASE_URL = "http://api.football-data.org/"

    var retrofit: Retrofit? = null
    //var interceptorFootballAPI: InterceptorFootballAPI? = null

    constructor(TOKEN: String){
        var Token = "Token " + TOKEN
        val interceptorAPI = InterceptorAPI(Token)

        val builderCliente = OkHttpClient.Builder()
        builderCliente.addInterceptor(interceptorAPI)
        val cliente = builderCliente.build()

        val builderRetrofit = Retrofit.Builder()
        retrofit = builderRetrofit
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cliente)
                .build()
    }
}
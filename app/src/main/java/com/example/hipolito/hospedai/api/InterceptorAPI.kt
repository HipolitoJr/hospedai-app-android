package com.example.hipolito.hospedai.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by hipolito on 27/02/18.
 */
class InterceptorAPI(public var TOKEN: String): Interceptor {

    val AUTHORIZATION = "Authorization"

    override fun intercept(chain: Interceptor.Chain?): Response? {

        val request = chain!!.request().newBuilder()
                .addHeader(AUTHORIZATION, TOKEN)
                .build()

        return chain.proceed(request)
    }

}
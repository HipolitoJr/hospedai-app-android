package com.example.hipolito.hospedai.model

import com.google.gson.annotations.SerializedName

/**
 * Created by hipolito on 27/02/18.
 */
class TokenAPIModel(){

    @SerializedName("token") lateinit var token: String
    @SerializedName("detail") lateinit var erro: String

}
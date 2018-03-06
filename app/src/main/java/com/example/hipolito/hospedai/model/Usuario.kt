package com.example.hipolito.hospedai.model

/**
 * Created by hipolito on 27/02/18.
 */
class Usuario(
        var password: String,
        var username: String){

    var id: Long = 0
    lateinit var detail: String

}
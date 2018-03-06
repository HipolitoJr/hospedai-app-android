package com.example.hipolito.hospedai.model

import com.google.gson.annotations.SerializedName

/**
 * Created by hipolito on 04/03/18.
 */
class Hospede(
        var nome: String,
        var cpf: String,
        var telefone: String,
        var email: String,
        var endereco: String,
        @SerializedName("qtd_hospedagens") var qtdHospedagens: Int = 0){

    @SerializedName("id") var id = 0L
    @SerializedName("is_hospedado") var isHospedado = false

}
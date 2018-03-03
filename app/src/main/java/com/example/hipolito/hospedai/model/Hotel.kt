package com.example.hipolito.hospedai.model

import com.google.gson.annotations.SerializedName

/**
 * Created by hipolito on 03/03/18.
 */
class Hotel(
        @SerializedName("id") var id: Long,
        @SerializedName("usuario_id") var usuarioId: Long,
        @SerializedName("usuario") var usuario: Long,
        @SerializedName("razao_social") var razaoSocial: String,
        @SerializedName("telefone") var telefone: Long,
        @SerializedName("endereco") var endereco: String,
        @SerializedName("valor_diaria") var valorDiaria: Float) {


}
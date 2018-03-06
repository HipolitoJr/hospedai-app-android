package com.example.hipolito.hospedai.model

import com.google.gson.annotations.SerializedName

/**
 * Created by hipolito on 04/03/18.
 */
class Hospedagem (
        @SerializedName("hospede") var hospede: Hospede
    ) {
    @SerializedName("id") var id: Long = 0
    @SerializedName("valor_debito_atual") lateinit var valorDebito: String
    @SerializedName("data_checkout") lateinit  var dataCheckout: String
    @SerializedName("data_checkin") lateinit  var dataCheckin: String

}
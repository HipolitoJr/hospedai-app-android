package com.example.hipolito.hospedai.model

import com.google.gson.annotations.SerializedName

/**
 * Created by hipolito on 04/03/18.
 */
class Hospedagem (
        @SerializedName("hospede") var hospede: Hospede,
        @SerializedName("valor_debito_atual") var valorDebito: String,
        @SerializedName("data_checkout") var dataCheckout: String,
        @SerializedName("data_checkin") var dataCheckin: String
    ) {
    @SerializedName("id") var id: Long = 0

}
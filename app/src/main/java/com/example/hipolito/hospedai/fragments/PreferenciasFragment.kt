package com.example.hipolito.hospedai.fragments


import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.model.Hotel
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.fragment_preferencias.view.*
import kotlinx.android.synthetic.main.item_lista_hospedes.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferenciasFragment : Fragment() {

    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var progressLoadDialog: ProgressDialog
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_preferencias, container, false)
        initComponents()

        return mView
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(context)
        apiService = APIService(getToken())
        progressLoadDialog = initLoadDialog()

        progressLoadDialog.show()
        getHotel()

    }

    private fun getHotel() {
        val hotelCall = apiService.hotelEndPoint.getHotel(getHotelSelecionado())

        hotelCall.enqueue(object: Callback<Hotel> {
            override fun onFailure(call: Call<Hotel>?, t: Throwable?) {
                Toast.makeText(context, "Falha de conexão", Toast.LENGTH_SHORT).show()
                progressLoadDialog.hide()
            }

            override fun onResponse(call: Call<Hotel>?, response: Response<Hotel>?) {
                if (response!!.isSuccessful){
                    setValuesHotel(response.body())
                }else{
                    Toast.makeText(context, "Erro: " + response.errorBody().string(), Toast.LENGTH_SHORT).show()
                }
                progressLoadDialog.hide()
            }

        })
    }

    private fun initLoadDialog(): ProgressDialog {
        val pgDialog = ProgressDialog(context)
        pgDialog.setMessage("Aguarde...")
        return pgDialog
    }

    private fun setValuesHotel(hotel: Hotel) {

        mView.txtPreferencesNomeHotel.setText(hotel.razaoSocial)
        mView.txtPreferencesDiaria.setText("R$ " + hotel.valorDiaria.toString().replace(".", ","))
        mView.txtPreferencesTelefone.setText(hotel.telefone.toString())
        mView.txtPreferencesEndereco.setText(hotel.endereco)

    }

    private fun getHotelSelecionado(): Long {
        return securityPreferences.getSavedLong(HospedaiConstants.KEY.HOTEL_SELECIONADO)
    }

    private fun getToken(): String {
        return securityPreferences.getSavedString(HospedaiConstants.KEY.TOKEN_LOGADO)
    }

}

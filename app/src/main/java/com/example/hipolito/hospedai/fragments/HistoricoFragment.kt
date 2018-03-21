package com.example.hipolito.hospedai.fragments


import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.adapters.HistoricoRVAdapter
import com.example.hipolito.hospedai.adapters.HospedagensRVAdapter
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.model.Hospedagem
import com.example.hipolito.hospedai.model.Hospede
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.fragment_historico.*
import kotlinx.android.synthetic.main.fragment_hospedagens.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class HistoricoFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_historico, container, false)
        initComponents()

        return mView
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(context)
        apiService = APIService(getToken())
        progressDialog = initLoadDialog()

        progressDialog.show()
        getHistorico(getHotelSelecionado())


    }

    private fun getHistorico(hotelId: Long) {
        val historicoCall = apiService.historicoEndPoint.getHistorico(hotelId)

        historicoCall.enqueue(object: Callback<MutableList<Hospedagem>>{
            override fun onFailure(call: Call<MutableList<Hospedagem>>?, t: Throwable?) {
                Toast.makeText(context, "Falha de conexão", Toast.LENGTH_SHORT).show()
                progressDialog.hide()
            }

            override fun onResponse(call: Call<MutableList<Hospedagem>>?, response: Response<MutableList<Hospedagem>>?) {
                if (response!!.isSuccessful){
                    exibirLista(response.body())
                }else{
                    Toast.makeText(context, "" + response.errorBody().string(), Toast.LENGTH_SHORT).show()
                }
                progressDialog.hide()
            }
        })
    }

    private fun exibirLista(hospedagensList: MutableList<Hospedagem>){
        val historicoRVAdapter = HistoricoRVAdapter(context, activity as AppCompatActivity, hospedagensList)

        rvHistoricoFragment.adapter = historicoRVAdapter

        val linearLayoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.scrollToPosition(0)

        rvHistoricoFragment.layoutManager = linearLayoutManager
        rvHistoricoFragment.setHasFixedSize(true)
    }

    private fun initLoadDialog(): ProgressDialog {
        val pgDialog = ProgressDialog(context)
        pgDialog.setMessage("Aguarde...")
        return pgDialog
    }

    private fun getHotelSelecionado(): Long{
        return securityPreferences.getSavedLong(HospedaiConstants.KEY.HOTEL_SELECIONADO)
    }

    private fun getToken(): String{
        return securityPreferences.getSavedString(HospedaiConstants.KEY.TOKEN_LOGADO)
    }

}

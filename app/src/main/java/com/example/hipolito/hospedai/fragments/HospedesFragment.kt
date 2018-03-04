package com.example.hipolito.hospedai.fragments


import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.adapters.HospedesRVAdapter
import com.example.hipolito.hospedai.adapters.HoteisRVAdapter
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.model.Hospede
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.fragment_hospedes.*
import kotlinx.android.synthetic.main.fragment_hoteis.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospedesFragment : Fragment() {

    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var progressLoadDialog: ProgressDialog
    private lateinit var alertMsgDialog: AlertDialog.Builder
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_hospedes, container, false)
        initComponents()

        return mView
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(context)
        apiService = APIService(getToken())

        getHospedes()
    }

    private fun getHospedes(){

        val hospedesCall = apiService.hospedeEndPoint.getHospedes(getHotelSelecionado())

        hospedesCall.enqueue(object: Callback<MutableList<Hospede>> {
            override fun onFailure(call: Call<MutableList<Hospede>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<MutableList<Hospede>>?, response: Response<MutableList<Hospede>>?) {
                if (response!!.isSuccessful){
                    if (response.body().isNotEmpty()){
                        exibirLista(response.body())
                    }
                }else{

                }
            }
        })

    }

    private fun exibirLista(hospedesList: MutableList<Hospede>?) {
        val hospedesRVAdapter = HospedesRVAdapter(context, activity as AppCompatActivity, hospedesList!!)

        rvHospedesFragment.adapter = hospedesRVAdapter
        val linearLayoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.scrollToPosition(0)

        rvHospedesFragment.layoutManager = linearLayoutManager
        rvHospedesFragment.setHasFixedSize(true)
    }

    private fun getHotelSelecionado(): Long{
        return securityPreferences.getSavedLong(HospedaiConstants.KEY.HOTEL_SELECIONADO)
    }

    private fun getToken(): String {
        return securityPreferences.getSavedString(HospedaiConstants.KEY.TOKEN_LOGADO)
    }

}

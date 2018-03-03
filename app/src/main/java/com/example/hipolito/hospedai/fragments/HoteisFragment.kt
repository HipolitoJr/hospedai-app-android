package com.example.hipolito.hospedai.fragments


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.adapters.HoteisRVAdapter
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.model.Hotel
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.fragment_hoteis.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HoteisFragment : Fragment() {

    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var token: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view  = inflater!!.inflate(R.layout.fragment_hoteis, container, false)
        initComponents()

        return view
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(context)
        token = getToken()
        apiService = APIService(token)

        getHoteis()
    }

    private fun getHoteis() {

        val callHoteis = apiService.hotelEndPoint.getHoteis()

        callHoteis.enqueue(object: Callback<MutableList<Hotel>> {
            override fun onFailure(call: Call<MutableList<Hotel>>?, t: Throwable?) {
                txtErroHoteis.visibility = View.VISIBLE
                Toast.makeText(context, "Falha: " + t!!.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<MutableList<Hotel>>?, response: Response<MutableList<Hotel>>?) {
                if (response!!.isSuccessful){
                    exibirLista(response.body())
                }else{
                    txtErroHoteis.visibility = View.VISIBLE
                    Toast.makeText(context, "Erro: " + response.code() + " | " + response.errorBody().string(), Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun exibirLista(hoteisList: MutableList<Hotel>?) {

        //CRIAR ADAPTER E SETAR LISTA
        val hoteisRVAdapter = HoteisRVAdapter(context, hoteisList!!)

        rvHoteisFragment.adapter = hoteisRVAdapter
        val linearLayoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.scrollToPosition(0)

        rvHoteisFragment.layoutManager = linearLayoutManager
        rvHoteisFragment.setHasFixedSize(true)


    }

    private fun getToken(): String{
        return securityPreferences.getSavedString(HospedaiConstants.KEY.TOKEN_LOGADO)
    }

}

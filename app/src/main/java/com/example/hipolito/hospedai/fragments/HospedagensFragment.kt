package com.example.hipolito.hospedai.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.adapters.HospedagensRVAdapter
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.model.Hospedagem
import com.example.hipolito.hospedai.model.Hospede
import com.example.hipolito.hospedai.model.Hotel
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.dialog_add_hospedagem.*
import kotlinx.android.synthetic.main.dialog_add_hospedagem.view.*
import kotlinx.android.synthetic.main.dialog_add_hospede.view.*
import kotlinx.android.synthetic.main.fragment_hospedagens.*
import kotlinx.android.synthetic.main.fragment_hospedagens.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition

class HospedagensFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var progressDialog: ProgressDialog
    private lateinit var hospedesList: List<Hospede>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_hospedagens, container, false)
        initComponents()

        return mView
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(context)
        apiService = APIService(getToken())
        getHospedes()

        progressDialog = initLoadDialog()

        mView.fabAddHospedagens.setOnClickListener({
            exibirDialogAddHospedagem()
        })

        progressDialog.show()
        getHospedagens()
    }

    private fun getHospedagens(){
        val hospedagensCall = apiService.hospedagemEndPoint.getHospedagens(getHotelSelecionado())

        hospedagensCall.enqueue(object: Callback<MutableList<Hospedagem>>{
            override fun onFailure(call: Call<MutableList<Hospedagem>>?, t: Throwable?) {
                Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()
                progressDialog.hide()
            }

            override fun onResponse(call: Call<MutableList<Hospedagem>>?, response: Response<MutableList<Hospedagem>>?) {
                if (response!!.isSuccessful){
                    if (response.body().isNotEmpty()){
                        exibirLista(response.body())
                        layoutErroHospedagens.visibility = View.GONE
                    }else{
                        layoutErroHospedagens.visibility = View.VISIBLE
                    }
                }else{
                    Toast.makeText(context, "Erro: " + response.code(), Toast.LENGTH_SHORT).show()
                }
                progressDialog.hide()
            }
        })
    }

    private fun postHospedagem(hospede: Hospede, hospedagem: Hospedagem){
        val hospedagemCall = apiService.hospedagemEndPoint.postHospedagens(getHotelSelecionado(), hospede.id, hospedagem)

        hospedagemCall.enqueue(object: Callback<Hospedagem>{
            override fun onResponse(call: Call<Hospedagem>?, response: Response<Hospedagem>?) {
                if (response!!.isSuccessful){
                    Toast.makeText(context, "Adicionado com Sucesso!", Toast.LENGTH_LONG).show()
                    getHospedagens()
                }else{
                    Toast.makeText(context, "Erro: " + response.code(), Toast.LENGTH_LONG).show()
                    progressDialog.hide()
                }
            }

            override fun onFailure(call: Call<Hospedagem>?, t: Throwable?) {
                Toast.makeText(context, "Falha na conexão", Toast.LENGTH_LONG).show()
                progressDialog.hide()
            }
        })
    }

    private fun getHospedes(){
        val hospedesCall = apiService.hospedeEndPoint.getHospedes(getHotelSelecionado())

        hospedesCall.enqueue(object: Callback<MutableList<Hospede>>{
            override fun onResponse(call: Call<MutableList<Hospede>>?, response: Response<MutableList<Hospede>>?) {
                if (response!!.isSuccessful){
                    hospedesList = response.body()
                }
            }

            override fun onFailure(call: Call<MutableList<Hospede>>?, t: Throwable?) {

            }
        })
    }

    private fun exibirLista(hospedagensList: MutableList<Hospedagem>){
        val hospedagensRVAdapter = HospedagensRVAdapter(context, activity as AppCompatActivity, apiService, getHotelSelecionado(), hospedagensList)

        rvHospedagensFragment.adapter = hospedagensRVAdapter

        val linearLayoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.scrollToPosition(0)

        rvHospedagensFragment.layoutManager = linearLayoutManager
        rvHospedagensFragment.setHasFixedSize(true)
    }

    private fun exibirDialogAddHospedagem() {
        var dialog = AlertDialog.Builder(context)

        var view = activity.layoutInflater.inflate(R.layout.dialog_add_hospedagem, mView as ViewGroup, false)
        initSpinner(view)

        dialog.setTitle("Adicionar Hospedagem")
                .setView(view)
                .setPositiveButton("Ok", { dialogInterface, i ->
                    val posItem = view.spnAddHspdgHospede.selectedItemPosition
                    var hospede = hospedesList.get(posItem)
                    val hospedagem = Hospedagem(hospede)

                    progressDialog.show()
                    postHospedagem(hospede, hospedagem)
                })
                .setNegativeButton("Cancelar", null)
                .show()

    }

    private fun initSpinner(view: View) {

        var hospedes: MutableList<String> = ArrayList()
        for (hospede in hospedesList)
            hospedes.add(hospede.nome)

        val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, hospedes)

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        view.spnAddHspdgHospede.adapter = arrayAdapter

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

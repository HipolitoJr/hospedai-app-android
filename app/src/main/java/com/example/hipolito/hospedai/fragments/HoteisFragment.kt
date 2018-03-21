package com.example.hipolito.hospedai.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.dialog_add_hotel.view.*
import kotlinx.android.synthetic.main.fragment_hoteis.*
import kotlinx.android.synthetic.main.fragment_hoteis.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HoteisFragment : Fragment() {

    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var progressLoadDialog: ProgressDialog
    private lateinit var alertMsgDialog: AlertDialog.Builder
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_hoteis, container, false)
        initComponents()

        return mView
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(context)
        apiService = APIService(getToken())

        progressLoadDialog = initLoadDialog()

        mView.fabAddHoteis.setOnClickListener {
            initDialogAddHotel()
        }

        progressLoadDialog.show()
        getHoteis()
    }

    private fun getHoteis() {

        val callHoteis = apiService.hotelEndPoint.getHoteis()

        callHoteis.enqueue(object: Callback<MutableList<Hotel>> {
            override fun onFailure(call: Call<MutableList<Hotel>>?, t: Throwable?) {
                val snackbar = Snackbar.make(mView, "Impossível iniciar...", Snackbar.LENGTH_LONG)
                snackbar.setAction("OK", null)
                snackbar.show()

                progressLoadDialog.hide()
            }

            override fun onResponse(call: Call<MutableList<Hotel>>?, response: Response<MutableList<Hotel>>?) {
                if (response!!.isSuccessful){
                    if (response.body().isNotEmpty()) {
                        layoutErroHoteis.visibility = View.GONE
                        exibirLista(response.body())
                    }else{
                        exibirMsgAlerta()
                    }
                }else {
                    val snackbar = Snackbar.make(mView, "Erro " + response.code(), Snackbar.LENGTH_LONG)
                    snackbar.setAction("OK", null)
                    snackbar.show()
                }
                progressLoadDialog.hide()
            }
        })

    }

    private fun postHotel(hotel: Hotel) {

        val postHotel = apiService.hotelEndPoint.postHotel(hotel)

        postHotel.enqueue(object: Callback<Hotel>{
            override fun onFailure(call: Call<Hotel>?, t: Throwable?) {
                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                progressLoadDialog.hide()
            }

            override fun onResponse(call: Call<Hotel>?, response: Response<Hotel>?) {
                if (response!!.isSuccessful){
                    Toast.makeText(context, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                    getHoteis()
                }else{
                    Toast.makeText(context, "Erro: " + response.errorBody().string(), Toast.LENGTH_SHORT).show()
                    progressLoadDialog.hide()
                }
            }
        })

    }

    private fun exibirMsgAlerta() {
        layoutErroHoteis.visibility = View.VISIBLE
    }

    private fun exibirLista(hoteisList: MutableList<Hotel>?) {
        val hoteisRVAdapter = HoteisRVAdapter(context, activity as AppCompatActivity, hoteisList!!)

        rvHoteisFragment.adapter = hoteisRVAdapter
        val linearLayoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.scrollToPosition(0)

        rvHoteisFragment.layoutManager = linearLayoutManager
        rvHoteisFragment.setHasFixedSize(true)

        exibirMsgDialog()
    }

    private fun initLoadDialog(): ProgressDialog {
        val pgDialog = ProgressDialog(context)
        pgDialog.setMessage("Aguarde...")
        return pgDialog
    }

    private fun exibirMsgDialog(){
        var hotel = securityPreferences.getSavedLong(HospedaiConstants.KEY.HOTEL_SELECIONADO)

        if (hotel == -1L){
            alertMsgDialog = AlertDialog.Builder(context)
                    .setTitle("Selecione...")
                    .setMessage("Escolha um Hotel para iniciar a sessão.")
                    .setPositiveButton("OK", null)
            alertMsgDialog.show()
        }

    }

    private fun initDialogAddHotel() {

        var dialog = AlertDialog.Builder(context)

        var view = activity.layoutInflater.inflate(R.layout.dialog_add_hotel, mView as ViewGroup, false)

        dialog.setTitle("Adicionar Hotel")
                .setView(view)
                .setPositiveButton("Ok", { dialogInterface, i ->

                    var razaoSocial = view.editAddHotelNome.text.toString()
                    var valorDiaria = view.editAddHotelDiaria.text.toString().toFloat()
                    var telefone = view.editAddHotelTelefone.text.toString().toLong()
                    var endereco = view.editAddHotelEndereco.text.toString()

                    var novoHotel = Hotel(razaoSocial, telefone, endereco, valorDiaria)
                    progressLoadDialog.show()
                    postHotel(novoHotel)
                })
                .setNegativeButton("Cancelar", null)
                .show()

    }

    private fun getToken(): String{
        return securityPreferences.getSavedString(HospedaiConstants.KEY.TOKEN_LOGADO)
    }

}

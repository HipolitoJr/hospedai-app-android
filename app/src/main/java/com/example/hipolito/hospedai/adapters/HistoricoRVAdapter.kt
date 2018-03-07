package com.example.hipolito.hospedai.adapters

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.hipolito.hospedai.HomeActivity
import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.fragments.HospedagensFragment
import com.example.hipolito.hospedai.model.Hospedagem
import com.example.hipolito.hospedai.model.Hotel
import kotlinx.android.synthetic.main.item_lista_historico.view.*
import kotlinx.android.synthetic.main.item_lista_hospedagens.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by hipolito on 04/03/18.
 */
class HistoricoRVAdapter(
        var context: Context,
        var activity: AppCompatActivity,
        var hospedagens: MutableList<Hospedagem>
    ): RecyclerView.Adapter<HistoricoRVAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var txtNomeHospede: TextView
        var txtDtEntrada: TextView
        var txtValorDiaria: TextView

        init {
            txtNomeHospede = itemView!!.findViewById(R.id.txtItemHistoricoNome)
            txtDtEntrada = itemView!!.findViewById(R.id.txtItemHistoricoDtEntrada)
            txtValorDiaria = itemView!!.findViewById(R.id.txtItemHistoricoValor)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var contexto = parent!!.context
        var inflater = LayoutInflater.from(contexto)

        val view = inflater.inflate(R.layout.item_lista_historico, parent, false)

        var viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var hospedagem = hospedagens.get(position)

        var entrada = hospedagem.dataCheckin.split("T")[0]
        var saida = hospedagem.dataCheckout.split("T")[0]

        holder!!.txtNomeHospede.setText(""+ hospedagem.hospede.nome)
        holder!!.txtDtEntrada.setText("Entrada: " + entrada)
        holder!!.itemView.txtItemHistoricoDtSaida.setText("Saida: " + saida)
        holder!!.txtValorDiaria.setText("R$ " + hospedagem.valorDebito.replace(".", ","))

    }

    override fun getItemCount(): Int {
        return hospedagens.size
    }
}
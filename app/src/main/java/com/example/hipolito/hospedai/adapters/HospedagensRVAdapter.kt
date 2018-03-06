package com.example.hipolito.hospedai.adapters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hipolito.hospedai.HomeActivity
import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.model.Hospedagem
import kotlinx.android.synthetic.main.item_lista_hospedagens.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by hipolito on 04/03/18.
 */
class HospedagensRVAdapter(
        var context: Context,
        var activity: AppCompatActivity,
        var hospedagens: MutableList<Hospedagem>
    ): RecyclerView.Adapter<HospedagensRVAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var txtNomeHospede: TextView
        var txtDtEntrada: TextView
        var txtValorDiaria: TextView

        init {
            txtNomeHospede = itemView!!.findViewById(R.id.txtItemHspdgNome)
            txtDtEntrada = itemView!!.findViewById(R.id.txtItemHspdgDtEntrada)
            txtValorDiaria = itemView!!.findViewById(R.id.txtItemHspdgValor)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var contexto = parent!!.context
        var inflater = LayoutInflater.from(contexto)

        val view = inflater.inflate(R.layout.item_lista_hospedagens, parent, false)

        var viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var hospedagem = hospedagens.get(position)

        holder!!.txtNomeHospede.setText(""+ hospedagem.hospede.nome)
        holder!!.txtDtEntrada.setText(hospedagem.dataCheckin)
        holder!!.txtValorDiaria.setText("R$ " + hospedagem.valorDebito.replace(".", ","))

    }

    override fun getItemCount(): Int {
        return hospedagens.size
    }
}
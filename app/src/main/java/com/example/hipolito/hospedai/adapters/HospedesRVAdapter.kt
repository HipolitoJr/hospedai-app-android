package com.example.hipolito.hospedai.adapters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.model.Hospedagem
import com.example.hipolito.hospedai.model.Hospede
import kotlinx.android.synthetic.main.item_lista_hospedes.view.*
import kotlinx.android.synthetic.main.item_lista_hoteis.view.*

/**
 * Created by hipolito on 04/03/18.
 */
class HospedesRVAdapter(
        var context: Context,
        var activity: AppCompatActivity,
        var hospedes: MutableList<Hospede>):
        RecyclerView.Adapter<HospedesRVAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var contexto = parent!!.context
        var inflater = LayoutInflater.from(contexto)

        val view = inflater.inflate(R.layout.item_lista_hospedes, parent, false)

        var viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var hospede = hospedes.get(position)

        holder!!.itemView.txtItemHospedeNome.setText(hospede.nome)
        holder!!.itemView.txtItemHospedeEmail.setText(hospede.email)
        holder!!.itemView.txtItemHospedeQtdHosp.setText("" + hospede.qtdHospedagens + " HOSPEDAGENS")

        if (hospede.isHospedado){
            holder!!.itemView.ivItemHospedeStatus.setImageResource(R.drawable.ic_ativo)
        }else{
            holder!!.itemView.ivItemHospedeStatus.setImageResource(R.drawable.ic_inativo)
        }

    }

    override fun getItemCount(): Int {
        return hospedes.size
    }
}
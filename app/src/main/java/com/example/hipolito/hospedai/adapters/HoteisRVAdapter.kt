package com.example.hipolito.hospedai.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.model.Hotel
import org.w3c.dom.Text

/**
 * Created by hipolito on 03/03/18.
 */
class HoteisRVAdapter(
        var context: Context,
        var hoteis:MutableList<Hotel>
    ): RecyclerView.Adapter<HoteisRVAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var txtNomeHotel: TextView
        lateinit var txtEnderecoHotel: TextView

        init {
            txtNomeHotel = itemView.findViewById<TextView>(R.id.txtItemHotelNome)
            txtEnderecoHotel = itemView.findViewById<TextView>(R.id.txtItemHotelEndereco)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var contexto = parent!!.context
        var inflater = LayoutInflater.from(contexto)

        val view = inflater.inflate(R.layout.item_lista_hoteis, parent, false)

        var viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var hotel = hoteis.get(position)

        holder!!.txtNomeHotel.setText(hotel.razaoSocial)
        holder!!.txtEnderecoHotel.setText(hotel.endereco)

    }

    override fun getItemCount(): Int {
        return hoteis.size
    }
}
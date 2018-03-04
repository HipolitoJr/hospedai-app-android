package com.example.hipolito.hospedai.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.example.hipolito.hospedai.R
import com.example.hipolito.hospedai.fragments.HospedagensFragment
import com.example.hipolito.hospedai.model.Hotel
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.item_lista_hoteis.view.*
import org.w3c.dom.Text

/**
 * Created by hipolito on 03/03/18.
 */
class HoteisRVAdapter(
        var context: Context,
        var activity: AppCompatActivity,
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var hotel = hoteis.get(position)

        holder.txtNomeHotel.setText(hotel.razaoSocial)
        holder.txtEnderecoHotel.setText(hotel.endereco)
        holder.itemView.btnItemHotelSelecionar.setOnClickListener({
            salvarHotel(hotel)
        })

    }

    private fun salvarHotel(hotel: Hotel) {
        var securityPreferences = SecurityPreferences(context)
        securityPreferences.saveLong(HospedaiConstants.KEY.HOTEL_SELECIONADO, hotel.id)
        initMsgDialog("Concluído",
                "" + hotel.razaoSocial + " Selecionado com sucesso!")
                .show()
    }

    private fun initMsgDialog(title: String, msg: String): AlertDialog.Builder {
        val pgDialog = AlertDialog.Builder(context)
        pgDialog.setTitle(title)
        pgDialog.setMessage(msg)
        pgDialog.setPositiveButton("OK", { dialogInterface, i -> setFragment() })
        pgDialog.setNegativeButton("Cancelar", null)
        return pgDialog
    }

    private fun setFragment(){
        var fragment: Fragment
        fragment = HospedagensFragment()

        if (fragment != null){
            var fragmentManager = activity.supportFragmentManager
            var ft = fragmentManager.beginTransaction()

            ft.replace(R.id.flFragment, fragment)
            ft.commit()
        }
    }

    override fun getItemCount(): Int {
        return hoteis.size
    }
}
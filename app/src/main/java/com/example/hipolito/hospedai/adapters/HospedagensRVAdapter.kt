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
class HospedagensRVAdapter(
        var context: Context,
        var activity: AppCompatActivity,
        var apiService: APIService,
        var hotelSelecionado: Long,
        var hospedagens: MutableList<Hospedagem>
    ): RecyclerView.Adapter<HospedagensRVAdapter.ViewHolder>() {

    private lateinit var progressDialog: ProgressDialog

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

        var entrada = hospedagem.dataCheckin.split("T")[0]

        holder!!.txtNomeHospede.setText(""+ hospedagem.hospede.nome)
        holder!!.txtDtEntrada.setText("Entrada: " + entrada)
        holder!!.txtValorDiaria.setText("R$ " + hospedagem.valorDebito.replace(".", ","))

        holder!!.itemView.ivItemHspdgCheck.setOnClickListener {
            fazerCheckout(hospedagem)
            progressDialog = initLoadDialog()
            progressDialog.show()
        }
    }

    private fun fazerCheckout(hospedagem: Hospedagem) {
        var call = apiService.hospedagemEndPoint.checkoutHospedagem(hotelSelecionado, hospedagem.id, hospedagem.hospede)

        call.enqueue(object: Callback<Hospedagem>{
            override fun onFailure(call: Call<Hospedagem>?, t: Throwable?) {
                Toast.makeText(context, "Erro conexão", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Hospedagem>?, response: Response<Hospedagem>?) {
                if (response!!.code() >= 200 && response!!.code() < 300){
                    Toast.makeText(context, "Movido para o Historico!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, HomeActivity::class.java)
                    context.startActivity(intent)
                    activity.finish()
                }else{
                    Toast.makeText(context, "" + response.errorBody().string(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initLoadDialog(): ProgressDialog {
        val pgDialog = ProgressDialog(context)
        pgDialog.setMessage("Aguarde...")
        return pgDialog
    }

    override fun getItemCount(): Int {
        return hospedagens.size
    }
}
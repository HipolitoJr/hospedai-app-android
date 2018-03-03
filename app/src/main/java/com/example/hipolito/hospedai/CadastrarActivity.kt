package com.example.hipolito.hospedai

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.model.Usuario
import kotlinx.android.synthetic.main.activity_cadastrar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastrarActivity : AppCompatActivity() {

    private lateinit var apiService: APIService
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)
        initComponents()

    }

    private fun initComponents() {

        apiService = APIService("")

        progressDialog = initPgDialog()

        btnCadastrar.setOnClickListener({
            progressDialog.show()
            registrarUsuario(criarUsuario())

        })

    }

    private fun initPgDialog(): ProgressDialog {
        val pgDialog = ProgressDialog(this)
        pgDialog.setMessage("Aguarde...")
        return pgDialog
    }

    private fun registrarUsuario(usuario: Usuario) {

        val cadastroUsuario = apiService.cadastroEndPoint.cadastroUsuario(usuario)

        cadastroUsuario.enqueue(object : Callback<Usuario>{
            override fun onFailure(call: Call<Usuario>?, t: Throwable?) {
                progressDialog.hide()
                Toast.makeText(this@CadastrarActivity, "Failure: " + t!!.cause.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Usuario>?, response: Response<Usuario>?) {
                if (response!!.isSuccessful){
                    Toast.makeText(this@CadastrarActivity, "Registrado!", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    progressDialog.hide()
                    Toast.makeText(this@CadastrarActivity, "Erro: " + response.code(), Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    private fun criarUsuario(): Usuario {
        val username = editUsuarioCadastrar.text.toString()
        val password = editSenhaCadastrar.text.toString()

        val usuario = Usuario(password, username)
        return usuario
    }
}

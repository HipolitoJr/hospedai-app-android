package com.example.hipolito.hospedai

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.model.TokenAPIModel
import com.example.hipolito.hospedai.model.Usuario
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponents()
    }

    private fun initComponents() {
        apiService = APIService("")
        securityPreferences = SecurityPreferences(this)
        progressDialog = initPgDialog()

        if (estaLogado()){
            initNextActivity()
        }

        btnEntrarLogin.setOnClickListener {
            progressDialog.show()
            fazerLogin(criarUsuario())
        }

        btnCadastrarLogin.setOnClickListener {
            val intent = Intent(this, CadastrarActivity::class.java)
            startActivity(intent)
        }

    }

    private fun fazerLogin(usuario: Usuario){

        val loginAPI = apiService.loginEndPoint.loginAPI(usuario)

        loginAPI.enqueue(object : Callback<TokenAPIModel>{
            override fun onFailure(call: Call<TokenAPIModel>?, t: Throwable?) {
                progressDialog.hide()
                Toast.makeText(this@LoginActivity, "Failure " + t!!.cause.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TokenAPIModel>?, response: Response<TokenAPIModel>?) {
                if (response!!.isSuccessful){
                    logarUsuario(response.body(), usuario)
                }else{
                    progressDialog.hide()
                    Toast.makeText(this@LoginActivity, "Erro", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun criarUsuario(): Usuario{
        val username = editUsuarioLogin.text.toString()
        val password = editSenhaLogin.text.toString()

        val usuario = Usuario(password, username)
        return usuario
    }

    private fun logarUsuario(token: TokenAPIModel, usuario: Usuario){
        securityPreferences.saveString(HospedaiConstants.KEY.TOKEN_LOGADO, token.token)
        securityPreferences.saveLong(HospedaiConstants.KEY.USUARIO_LOGADO, usuario.id)

        initNextActivity()
    }

    private fun estaLogado(): Boolean{
        var token = securityPreferences.getSavedString(HospedaiConstants.KEY.TOKEN_LOGADO)
        return if (token == "") false else true
    }

    private fun initNextActivity(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initPgDialog(): ProgressDialog {
        val pgDialog = ProgressDialog(this)
        pgDialog.setMessage("Aguarde...")
        return pgDialog
    }

}

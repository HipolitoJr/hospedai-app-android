package com.example.hipolito.hospedai

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.models.TokenAPIModel
import com.example.hipolito.hospedai.models.Usuario
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()

    }

    private fun setupViews() {
        apiService = APIService("")
        securityPreferences = SecurityPreferences(this)

        btnEntrarLogin.setOnClickListener {
            fazerLogin(criarUsuario())
        }

    }

    private fun fazerLogin(usuario: Usuario){

        val loginAPI = apiService.loginEndPoint.loginAPI(usuario)

        loginAPI.enqueue(object : Callback<TokenAPIModel>{
            override fun onFailure(call: Call<TokenAPIModel>?, t: Throwable?) {
                Toast.makeText(this@LoginActivity, "Failure " + t!!.cause.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TokenAPIModel>?, response: Response<TokenAPIModel>?) {
                if (response!!.isSuccessful){
                    logarUsuario(response.body(), usuario)
                }else{
                    Toast.makeText(this@LoginActivity, "Erro: " + response.body().erro, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun criarUsuario(): Usuario{
        val username = editUsuarioLogin.text.toString()
        val password = editSenhaLogin.text.toString()

        val usuario = Usuario(0, password, username)
        return usuario
    }

    private fun logarUsuario(token: TokenAPIModel, usuario: Usuario){

        securityPreferences.saveString(HospedaiConstants.KEY.TOKEN_LOGADO, token.token)
        securityPreferences.saveLong(HospedaiConstants.KEY.USUARIO_LOGADO, usuario.id)

        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)

    }

}

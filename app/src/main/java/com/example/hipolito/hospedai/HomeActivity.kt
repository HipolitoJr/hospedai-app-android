package com.example.hipolito.hospedai

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.hipolito.hospedai.api.APIService
import com.example.hipolito.hospedai.fragments.*
import com.example.hipolito.hospedai.model.Usuario
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        initComponents()

    }

    private fun initComponents() {
        title = "Hospedai"
        securityPreferences = SecurityPreferences(this)
        apiService = APIService(getToken())
        initNavigation()
        getUsuario()

        redirecionaFragment()
    }

    private fun getUsuario(){
        val usuarioCall = apiService.usuarioEndPoint.getUsuario(getUsuarioLogado())

        usuarioCall.enqueue(object: Callback<Usuario>{
            override fun onResponse(call: Call<Usuario>?, response: Response<Usuario>?) {
                if(response!!.isSuccessful){
                    setValuesLogado(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Usuario>?, t: Throwable?) {
                Toast.makeText(this@HomeActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setValuesLogado(usuario: Usuario) {
        val txtUsuario = findViewById<TextView>(R.id.txtHomeUsuario)
        val txtEmail = findViewById<TextView>(R.id.txtHomeEmail)
        txtUsuario.setText(usuario.username)
        txtEmail.setText(usuario.email)
    }

    private fun initNavigation() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_home_hoteis -> {
                fragment = HoteisFragment()
                title = "Meus Hoteis"
            }

            R.id.nav_home_hospedes -> {
                fragment = HospedesFragment()
                title = "Meus Hospedes"
            }

            R.id.nav_home_hospedagens -> {
                fragment = HospedagensFragment()
                title = "Hospedagens"
            }

            R.id.nav_home_historico -> {
                fragment = HistoricoFragment()
                title = "Historico"
            }

            R.id.nav_home_preferencias -> {
                fragment = PreferenciasFragment()
            }

            R.id.nav_home_logout -> {
                securityPreferences.limpar()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }

        if (fragment != null)
            setFragment(fragment!!)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun redirecionaFragment() {
        if (getHotelSelecionado() != -1L){
            setFragment(HospedagensFragment())
        }else{
            setFragment(HoteisFragment())
        }

    }

    private fun getHotelSelecionado(): Long{
        return securityPreferences.getSavedLong(HospedaiConstants.KEY.HOTEL_SELECIONADO)
    }

    private fun getToken(): String{
        return securityPreferences.getSavedString(HospedaiConstants.KEY.TOKEN_LOGADO)
    }

    private fun getUsuarioLogado(): Long{
        return securityPreferences.getSavedLong(HospedaiConstants.KEY.USUARIO_LOGADO)
    }

    private fun setFragment(fragment: Fragment){
        if (fragment != null){
            var fragmentManager = supportFragmentManager
            var ft = fragmentManager.beginTransaction()

            ft.replace(R.id.flFragment, fragment)
            ft.commit()
        }
    }
}

package com.example.hipolito.hospedai

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.hipolito.hospedai.fragments.HospedagensFragment
import com.example.hipolito.hospedai.fragments.HospedesFragment
import com.example.hipolito.hospedai.fragments.HoteisFragment
import com.example.hipolito.hospedai.util.HospedaiConstants
import com.example.hipolito.hospedai.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        initComponents()

    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(this)
        initNavigation()

        redirecionaFragment()
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
        }

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

    private fun setFragment(fragment: Fragment){
        if (fragment != null){
            var fragmentManager = supportFragmentManager
            var ft = fragmentManager.beginTransaction()

            ft.replace(R.id.flFragment, fragment)
            ft.commit()
        }
    }
}

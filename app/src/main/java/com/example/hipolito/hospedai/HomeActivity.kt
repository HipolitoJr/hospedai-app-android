package com.example.hipolito.hospedai

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.hipolito.hospedai.fragments.HoteisFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        initComponents()

    }

    private fun initComponents() {
        initNavigation()
        setFragment(HoteisFragment())
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
        lateinit var fragment: Fragment

        when (item.itemId) {
            R.id.nav_home_hoteis -> {
                fragment = HoteisFragment()
            }
        }

        setFragment(fragment)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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

package com.example.hipolito.hospedai.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hipolito.hospedai.R

class HoteisFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view  = inflater!!.inflate(R.layout.fragment_hoteis, container, false)


        return view
    }

}

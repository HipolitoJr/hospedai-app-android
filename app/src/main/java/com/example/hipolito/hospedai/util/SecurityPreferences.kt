package com.example.hipolito.hospedai.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by hipolito on 01/03/18.
 */
class SecurityPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("hospedai", Context.MODE_PRIVATE)


    fun saveString(key: String, value: String){
        sharedPreferences.edit().putString(key, value)
    }

    fun getSavedString(key: String): String{
        return sharedPreferences.getString(key, null)
    }

    fun saveInt(key: String, value: Int){
        sharedPreferences.edit().putInt(key, value)
    }

    fun getSavedInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun saveFloat(key: String, value: Float){
        sharedPreferences.edit().putFloat(key, value)
    }

    fun getSavedFloat(key: String): Float{
        return sharedPreferences.getFloat(key, -1F)
    }

    fun saveBoolean(key: String, value: Boolean){
        sharedPreferences.edit().putBoolean(key, value)
    }

    fun getSavedBoolean(key: String): Boolean{
        return sharedPreferences.getBoolean(key, false)
    }

    fun saveLong(key: String, value: Long){
        sharedPreferences.edit().putLong(key, value)
    }

    fun getSavedLong(key: String): Long{
        return sharedPreferences.getLong(key, -1L)
    }

}
package com.example.quizy.data.common

import android.content.Context
import android.content.SharedPreferences

object SharedPreferensesProvider {
    private var sharedPrefs: SharedPreferences?= null

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
    }

    fun setIdToSharedPrefs(id: Int){
        sharedPrefs!!.edit().putInt("USER_ID", id).apply()
    }
    fun getIdFromSharedPrefs():Int=
        sharedPrefs!!.getInt("USER_ID", -1)





}
package br.com.hivecode.supergames.data.dao

import android.content.Context.MODE_PRIVATE
import br.com.hivecode.supergames.App

class SharedPreferencesDao {

    companion object {
        const val PREFS_NAME : String = "preferences"
    }

    fun saveBool(key : String = "key", value : Boolean = false ){
        val editor = App.applicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun loadBool(key : String = "key"): Boolean {
        val editor = App.applicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return editor.getBoolean(key, false)
    }


}
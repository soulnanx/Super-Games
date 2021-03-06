package br.com.hivecode.supergames

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import br.com.hivecode.supergames.data.dao.SharedPreferencesDao
import com.facebook.stetho.Stetho

class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext() : App {
            return instance!!.applicationContext as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

    fun hasInternetConnection(): Boolean {
        val cm = App.applicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun hasCache() : Boolean{
        return SharedPreferencesDao().loadBool("cache")
    }

    fun setCache(boolean: Boolean) {
        SharedPreferencesDao().saveBool("cache", boolean)
    }

}
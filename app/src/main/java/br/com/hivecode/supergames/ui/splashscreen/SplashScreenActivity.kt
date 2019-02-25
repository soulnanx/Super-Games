package br.com.hivecode.supergames.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.ui.games.GamesActivity

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val TWO_SECONDS : Long = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        init()
    }

    private fun init() {
        Handler().postDelayed(
            { navigateToGames() },
            TWO_SECONDS
        )
    }

    private fun navigateToGames() {
        val intent = GamesActivity.newIntent(this@SplashScreenActivity)
        startActivity(intent)
    }
}
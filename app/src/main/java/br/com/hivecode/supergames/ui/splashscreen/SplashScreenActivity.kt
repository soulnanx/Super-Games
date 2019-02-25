package br.com.hivecode.supergames.ui.splashscreen

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.ui.games.GamesActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

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
        startAnimation()
        navigateToGames()

    }

    @SuppressLint("NewApi")
    private fun startAnimation() {
        if (logo.drawable is AnimatedVectorDrawable){
            val vectorDrawable = logo.drawable as AnimatedVectorDrawable
            Handler().post {vectorDrawable.start()}
            Handler().postDelayed({vectorDrawable.start()}, 1000)
        }

    }

    private fun navigateToGames() {

        Handler().postDelayed(
            {
                val intent = GamesActivity.newIntent(this@SplashScreenActivity)
                this@SplashScreenActivity.startActivity(intent)
            },
            TWO_SECONDS
        )


    }
}
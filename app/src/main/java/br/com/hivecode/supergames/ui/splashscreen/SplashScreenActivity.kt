package br.com.hivecode.supergames.ui.splashscreen

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v7.app.AppCompatActivity
import br.com.hivecode.supergames.ui.games.GamesActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import android.content.Intent
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.data.entity.Person
import br.com.hivecode.supergames.data.entity.User
import br.com.hivecode.supergames.data.entity.UserB
import java.math.BigDecimal


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
        if (logo.drawable is AnimatedVectorDrawableCompat ) {
            val drawable = logo.drawable as AnimatedVectorDrawableCompat
            Handler().post {drawable.start()}
            Handler().postDelayed({drawable.start()}, 1000)
        } else {
            val drawable = logo.drawable as AnimatedVectorDrawable
            Handler().post {drawable.start()}
            Handler().postDelayed({drawable.start()}, 1000)
        }

    }

    private fun navigateToGames() {
        val userB = UserB.Builder()
            .address("rua hipodromo")
            .age(56)
            .name("jonas")
            .build()

        val user = User.Builder()
            .setAddress("rua hipodromo")
            .setAge(54)
            .setName("bruno")
            .create()

        val person = Person.Builder()
            .job("developer")
            .name("renan")
            .salary(BigDecimal(2000))
            .build()



        Handler().postDelayed(
            {
                val intent = GamesActivity.newIntent(this@SplashScreenActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                this@SplashScreenActivity.startActivity(intent)
            },
            TWO_SECONDS
        )


    }
}
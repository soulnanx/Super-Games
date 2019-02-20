package br.com.hivecode.supergames.ui.games

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.data.entity.Item
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_games_detail.*

class GamesDetailActivity : AppCompatActivity() {

    private lateinit var viewModel : GamesViewModel

    companion object {
        const val ITEM: String = "item"

        fun newIntent(context: Context) : Intent {
            return Intent(context, GamesDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_detail)
        init()
    }

    private fun init(){
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        loadGame()
    }

    private fun loadGame() {
        val itemGame = intent.extras.getSerializable(ITEM) as Item
        setValues(itemGame)
    }

    private fun setValues(itemGame: Item) {
        activity_games_detail_title_txt.text = itemGame.game.name
        activity_games_detail_viewers_txt.text = itemGame.viewers.toString()
        activity_games_detail_channels_txt.text = itemGame.channels.toString()
        Glide.with(this@GamesDetailActivity)
            .load(itemGame.game.logo?.large)
            .into(activity_games_detail_image)
    }
}
package br.com.hivecode.supergames.ui.games

import android.arch.lifecycle.ViewModel
import br.com.hivecode.supergames.data.entity.Item
import br.com.hivecode.supergames.data.repository.GameRepository
import br.com.hivecode.supergames.data.api.response.TopGamesResponse

class GamesViewModel : ViewModel() {

    lateinit var selectedGame : Item

    fun getTopGames(offset : Int = 0,
                    callbackOnline: (TopGamesResponse) -> Unit,
                    callbackOffline: (MutableList<Item>) -> Unit,
                    callbackFailure: (String) -> Unit){

        GameRepository().getTopGames(offset, callbackOnline, callbackOffline, callbackFailure)

    }
}
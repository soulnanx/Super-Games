package br.com.hivecode.supergames.data.repository

import br.com.hivecode.supergames.App
import br.com.hivecode.supergames.data.dao.AppDatabase
import br.com.hivecode.supergames.data.api.GameAPI
import br.com.hivecode.supergames.data.entity.Item
import br.com.hivecode.supergames.data.api.response.TopGamesResponse
import kotlinx.coroutines.*
import java.lang.Exception

class GameRepository {
    private val db = AppDatabase.getInstance(App.applicationContext())!!

    fun getTopGames(offset : Int = 0,
                    callbackOnline: (TopGamesResponse) -> Unit,
                    callbackOffline: (MutableList<Item>) -> Unit,
                    callbackFailure: (String) -> Unit){
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        scope.launch {
            try {
                if (App.applicationContext().hasInternetConnection()){
                    var gamesResponse = GameAPI.gamesService.getTopGames(offset).await()
                    callbackOnline(gamesResponse)
                    gamesResponse.top.forEach { item -> add(item) }
                } else {
                    var games = GlobalScope.async {db.itemDao().getAll()}.await()
                    callbackOffline(games)
                }
            } catch (e : Exception){
                callbackFailure("Alguma coisa deu muito errado =(")
            }
        }
    }

    suspend fun add(item: Item) : Long{
        return GlobalScope.async {
            db.itemDao().create(item)
        }.await()
    }
}


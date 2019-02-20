package br.com.hivecode.supergames.data.repository

import br.com.hivecode.supergames.App
import br.com.hivecode.supergames.data.dao.conf.AppDatabase
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
                    callbackOfflineAndHasNoCache: (Unit) -> Unit,
                    callbackFailure: (Exception) -> Unit){
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        scope.launch {
            try {
                when {
                    App.applicationContext().hasInternetConnection() -> usingOnlineData(offset, callbackOnline)
                    App.applicationContext().hasCache()              -> usingOfflineData(callbackOffline)
                    else -> callbackOfflineAndHasNoCache(Unit)
                }
            } catch (e : Exception){
                callbackFailure(e)
            }
        }
    }

    private suspend fun usingOfflineData(callbackOffline: (MutableList<Item>) -> Unit) {
        var games = GlobalScope.async { db.itemDao().getAll() }.await()
        callbackOffline(games)
    }

    private suspend fun usingOnlineData(
        offset: Int,
        callbackOnline: (TopGamesResponse) -> Unit
    ) {
        var gamesResponse = GameAPI.gamesService.getTopGames(offset).await()
        callbackOnline(gamesResponse)
        createCache(gamesResponse)
    }

    private suspend fun createCache(gamesResponse: TopGamesResponse) {
        gamesResponse.top.forEach { item -> add(item) }
        App.applicationContext().setCache(true)
    }

    private suspend fun add(item: Item) : Long{
        return GlobalScope.async {
            db.itemDao().create(item)
        }.await()
    }
}


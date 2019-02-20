package br.com.hivecode.supergames.ui.games

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.data.entity.Item
import br.com.hivecode.supergames.data.api.response.TopGamesResponse
import kotlinx.android.synthetic.main.activity_games.*

class GamesActivity : AppCompatActivity() {

    private lateinit var viewModel : GamesViewModel
    private lateinit var customLayoutManager : GridLayoutManager
    private lateinit var customAdapter: GamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        customLayoutManager = GridLayoutManager(this@GamesActivity, 2)
        customAdapter = GamesAdapter(this@GamesActivity, mutableListOf()) { itemGame -> viewModel.selectedGame = itemGame}
        loadGames()
        setEvents()
    }

    private fun setEvents() {
        activity_games_swipe.setOnRefreshListener { onSwipeList() }
    }

    private fun onSwipeList() {
        activity_games_swipe.isRefreshing = true
        loadGames()
    }

    private fun loadGames(offset : Int = 0) {
        var isFirstLoad = offset == 0

        if (isFirstLoad){
            viewModel.getTopGames(offset,
                ::setOnlineList,
                ::setOfflineList,
                ::showError)
        } else {
            viewModel.getTopGames(offset,
                {it -> addMoreItems(it.top)},
                ::setOfflineList,
                ::showError)
        }
    }

    private fun showError(messageError: String) {
        showMessageError(messageError)    }

    private fun setOfflineList(games: MutableList<Item>) {
        customAdapter.clear()
        showMessageError(getString(R.string.offline_results))
        setList(games, true)
    }

    private fun setOnlineList(topGamesResponse: TopGamesResponse) {
        setList(topGamesResponse.top, false)
    }

    private fun showMessageError(message: String) {
        Toast.makeText(
            this@GamesActivity,
            message,
            Toast.LENGTH_SHORT
        ).show()
        activity_games_swipe.isRefreshing = false
    }

    private fun setList(games: MutableList<Item>, isFromCache: Boolean) {
        with(activity_games_rv) {
            adapter = customAdapter
            layoutManager = customLayoutManager
            if (isFromCache){
                customAdapter.update(games)
                clearOnScrollListeners()
            } else {
                customAdapter.update(games)
                addOnScrollListener(setupOnScrollListener(games))
            }

        }
        activity_games_swipe.isRefreshing = false
    }

    private fun setupOnScrollListener(games: MutableList<Item>): OnScrollListener {
        return OnScrollListener(
            customLayoutManager,
            customAdapter,
            games,
            ::loadGames
        )
    }

    private fun addMoreItems(games: MutableList<Item>) {
        customAdapter.update(games)
    }

}

class OnScrollListener(var layoutManager: GridLayoutManager,
                       val adapter: GamesAdapter,
                       val dataList: MutableList<Item>,
                       val loadMore: (Int) -> Unit) : RecyclerView.OnScrollListener() {
    var previousTotal = 0
    var loading = true
    val visibleThreshold = 10
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            val initialSize = dataList.size
            loadMore(adapter.itemCount)
            val updatedSize = dataList.size
            recyclerView.post { adapter.notifyItemRangeInserted(initialSize, updatedSize) }
            loading = true
        }
    }
}
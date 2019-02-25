package br.com.hivecode.supergames.ui.games

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.data.entity.Item
import br.com.hivecode.supergames.data.api.response.TopGamesResponse
import kotlinx.android.synthetic.main.activity_games.*
import kotlinx.android.synthetic.main.games_content.*
import kotlinx.android.synthetic.main.internet_error_content.*
import java.lang.Exception

class GamesActivity : AppCompatActivity() {

    companion object {
        const val ITEM: String = "item"

        fun newIntent(context: Context) : Intent {
            return Intent(context, GamesActivity::class.java)
        }
    }

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
        setRecyclerView()
        loadGames()
        setEvents()
    }

    private fun setRecyclerView() {
        customLayoutManager = GridLayoutManager(this@GamesActivity, 2)
        customAdapter =
                GamesAdapter(this@GamesActivity, mutableListOf()) { itemGame -> navigateToDetail(itemGame) }
        with(activity_games_rv) {
            adapter = customAdapter
            layoutManager = customLayoutManager
        }

    }

    private fun navigateToDetail(itemGame: Item) {
        val intent = GamesDetailActivity.newIntent(this@GamesActivity)
        val bundle = Bundle()
        bundle.putSerializable(GamesDetailActivity.ITEM, itemGame)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun setEvents() {
        activity_games_swipe.setOnRefreshListener { onSwipeList() }
        activity_games_content_try_again.setOnClickListener { onClickTryAgain() }
    }

    private fun onClickTryAgain() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.scale_effect)
        activity_games_content_try_again.startAnimation(animation)
        loadGames(0)
    }

    private fun onSwipeList() {
        activity_games_swipe.isRefreshing = true
        loadGames()
    }

    private fun loadGames(offset : Int = 0) {
        var isFirstLoad = offset == 0

        if (isFirstLoad){
            showLoading()
            viewModel.getTopGames(offset,
                ::setOnlineList,
                ::setOfflineList,
                ::hasNoCache,
                ::showError)
        } else {
            viewModel.getTopGames(offset,
                {it -> addMoreItems(it.top)},
                ::setOfflineList,
                ::hasNoCache,
                ::showError)
        }
    }

    private fun showLoading() {
        activity_games_content_loading.visibility = View.VISIBLE
        activity_games_rv.visibility = View.GONE
    }

    private fun hasNoCache( unit : Unit) {
        showTryAgain()
    }

    private fun showTryAgain() {
        activity_games_rv.visibility = View.GONE
        activity_games_content_no_internet.visibility = View.VISIBLE
        activity_games_content_loading.visibility = View.GONE
    }

    private fun showGames() {
        activity_games_rv.visibility = View.VISIBLE
        activity_games_content_no_internet.visibility = View.GONE
        activity_games_content_loading.visibility = View.GONE
    }

    private fun showError(ex: Exception) {
        showMessageError("Alguma coisa deu muito errado =(")    }

    private fun setOfflineList(games: MutableList<Item>) {
        showGames()
        customAdapter.clear()
        showMessageError(getString(R.string.offline_results))
        setList(games, true)
    }

    private fun setOnlineList(topGamesResponse: TopGamesResponse) {
        showGames()
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
        if (isFromCache){
            activity_games_rv.clearOnScrollListeners()
        } else {
            activity_games_rv.addOnScrollListener(setupOnScrollListener(games))
        }
        customAdapter.update(games)

        activity_games_swipe.isRefreshing = false
    }

    private fun setupOnScrollListener(games: MutableList<Item>): OnScrollListener {
        return OnScrollListener(
            customLayoutManager,
            customAdapter,
            games.size,
            ::loadGames
        )
    }

    private fun addMoreItems(games: MutableList<Item>) {
        customAdapter.update(games)
    }

}

class OnScrollListener(var layoutManager: GridLayoutManager,
                       val adapter: GamesAdapter,
                       val itemsCount: Int,
                       val loadMore: (Int) -> Unit) : RecyclerView.OnScrollListener() {
    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 10
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

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
            val initialSize = itemsCount
            loadMore(adapter.itemCount)

            val updatedSize = itemsCount
            recyclerView.post {
                adapter.notifyItemRangeInserted(initialSize, updatedSize)
                adapter.refresh()
            }
            loading = true
        }
    }
}
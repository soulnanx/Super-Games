package br.com.hivecode.supergames.ui.games

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.data.entity.Game
import br.com.hivecode.supergames.data.entity.Item
import br.com.hivecode.supergames.data.entity.Logo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_game.view.*

class GamesAdapter (private val context: Context,
                    private val items: MutableList<Item>,
                    private val gameClicked: (itemGame : Item) -> Unit) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        setTitle(holder, item.game.name)
        loadImage(holder, item.game?.logo)

        holder!!.card.setOnClickListener {
            gameClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun loadImage(holder: GamesAdapter.ViewHolder?, image : Logo?) {
        image.let {
            Glide.with(context)
                .load(it?.medium)
                .into(holder!!.image)
        }


    }

    private fun setTitle(holder: ViewHolder?, title: String?) {
        title.let {
            holder?.title?.text = title
        }
    }

    fun update(itemsNew : List<Item>){
        //items.clear()
        items.addAll(itemsNew)
        refresh()
    }

    fun clear(){
        items.clear()
        refresh()
    }

    fun refresh(){
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.item_game_title_txt!!
        val image = itemView.item_game_image!!
        val card = itemView.item_game_card!!
    }
}
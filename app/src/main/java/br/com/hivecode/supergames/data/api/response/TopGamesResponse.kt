package br.com.hivecode.supergames.data.api.response

import br.com.hivecode.supergames.data.entity.Item
import com.google.gson.annotations.SerializedName

class TopGamesResponse (@SerializedName(value = "_total") val total : Int,
                        val top : MutableList<Item>)
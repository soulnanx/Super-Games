package br.com.hivecode.supergames.data.dao

import android.arch.persistence.room.TypeConverter
import br.com.hivecode.supergames.data.entity.Game
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromJson(json: String?): Game?{
        return json?.let { Gson().fromJson(json, Game::class.java) }
    }

    @TypeConverter
    fun gameToJson(item: Game?): String? {
        return Gson().toJson(item) ?: ""
    }
}
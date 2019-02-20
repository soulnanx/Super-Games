package br.com.hivecode.supergames.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(indices = [Index("game", unique = true)])
data class Item (@PrimaryKey(autoGenerate = true) val id : Long = 0,
                 val game: Game,
                 val viewers : Int,
                 val channels : Int) : Serializable
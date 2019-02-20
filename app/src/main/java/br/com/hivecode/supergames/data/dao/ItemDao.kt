package br.com.hivecode.supergames.data.dao

import android.arch.persistence.room.*
import br.com.hivecode.supergames.data.entity.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun create(item: Item): Long

    @Update
    fun update(item: Item): Int

    @Delete
    fun delete(vararg item: Item): Int

    @Query("SELECT * FROM Item")
    fun getAll(): MutableList<Item>

}
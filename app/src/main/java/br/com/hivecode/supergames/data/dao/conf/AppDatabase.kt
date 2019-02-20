package br.com.hivecode.supergames.data.dao.conf

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Room
import android.content.Context
import br.com.hivecode.supergames.data.dao.ItemDao
import br.com.hivecode.supergames.data.entity.Item

@Database(entities = [Item::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase constructor() : RoomDatabase() {

    companion object {

        private const val DB_NAME = "app"
        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        internal fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance =
                        create(context)
            }
            return instance
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java,
                DB_NAME
            )
                .build()
        }

    }

    abstract fun itemDao() : ItemDao
}
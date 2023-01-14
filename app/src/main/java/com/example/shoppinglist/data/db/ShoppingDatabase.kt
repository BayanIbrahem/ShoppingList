package com.example.shoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem

@Database (
    entities = [ShoppingItem::class],
    version = 7
)
abstract class ShoppingDatabase: RoomDatabase() {

    abstract fun getShoppingListDao(): ShoppingListDao

    companion object {
        private val LOCK = Any()
        @Volatile // make sure that only one thread can reach this var at a time.
        private var instance: ShoppingDatabase? = null

        private fun createDB (context: Context): ShoppingDatabase {
            return Room.databaseBuilder(
                context,
                ShoppingDatabase::class.java,
                "shopping_list"
            ).build()
        }
        // other threads can't reach 'instance' while executing synchronized block
        operator fun invoke (context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDB(context).also { instance = it }
        }

    }
}
package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist.data.db.entities.ShoppingItem
import java.util.concurrent.Flow

/**
 * don't forget to mark functions as suspend
 */
@Dao
interface ShoppingListDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE, entity = ShoppingItem::class)
    fun insertItem (item: ShoppingItem): Long

    @Delete (entity = ShoppingItem::class)
    fun delete (item: ShoppingItem)

    @Query ("SELECT * FROM shopping_list")
    fun getAll(): MutableList<ShoppingItem>
}
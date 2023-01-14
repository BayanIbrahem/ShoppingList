package com.example.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.data.db.ShoppingDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem

class ShoppingListDatabaseRepo (private val db: ShoppingDatabase) {

    val dao = db.getShoppingListDao()

    suspend fun insertOrUpdate (item: ShoppingItem) {
        dao.insertItem(item)
    }
    suspend fun deleteItem (item: ShoppingItem) {
        dao.delete(item)
    }
    suspend fun getAllItems (): LiveData<List<ShoppingItem>> {
        return MutableLiveData(dao.getAll())
    }
}
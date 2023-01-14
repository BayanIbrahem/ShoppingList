package com.example.shoppinglist.ui.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.db.ShoppingListDao
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.data.repository.ShoppingListDatabaseRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ShoppingListViewModel (private val shoppingListRepo: ShoppingListDatabaseRepo): ViewModel() {
    var list = getAll()
    fun insertOrUpdate(item: ShoppingItem) = CoroutineScope(Dispatchers.IO).launch {
        shoppingListRepo.insertOrUpdate(item)
    }
    fun deleteItem (item: ShoppingItem) = CoroutineScope(Dispatchers.IO).launch {
        shoppingListRepo.deleteItem(item)
    }
    fun deleteItem (id: Long) = CoroutineScope(Dispatchers.IO).launch {
        shoppingListRepo.deleteItem(ShoppingItem(id = id, name = "nal", amount = -1))
    }
    fun getAll (): LiveData<List<ShoppingItem>> {
        var liveData: LiveData<List<ShoppingItem>>? = null
        CoroutineScope(Dispatchers.IO).launch {
            liveData =  async {
                shoppingListRepo.getAllItems()
            }.await()
        }
        return liveData ?: MutableLiveData()
    }
    fun refresh() {
        list = getAll()
    }
}
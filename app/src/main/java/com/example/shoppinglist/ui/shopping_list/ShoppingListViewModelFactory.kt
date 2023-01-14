package com.example.shoppinglist.ui.shopping_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.data.repository.ShoppingListDatabaseRepo

@Suppress("UNCHECKED_CAST")
class ShoppingListViewModelFactory (val repo: ShoppingListDatabaseRepo):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingListViewModel(repo) as T
    }
}
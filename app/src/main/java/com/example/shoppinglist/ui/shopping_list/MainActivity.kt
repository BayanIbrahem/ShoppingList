package com.example.shoppinglist.ui.shopping_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.data.db.ShoppingDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.data.repository.ShoppingListDatabaseRepo
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.recycler_view.RvShoppingAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ShoppingListViewModel
    lateinit var adapter: RvShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init () {
        initViewModel()
        initRvValues()
        setRv()
        setAddingItem()
    }
    private fun initViewModel() {

        val database = ShoppingDatabase(this)
        val repository = ShoppingListDatabaseRepo(database)
        val factory = ShoppingListViewModelFactory(repository)
        viewModel = ViewModelProvider(this,  factory)[ShoppingListViewModel::class.java]

    }
    private fun initRvValues() {
        adapter = RvShoppingAdapter(viewModel)
        viewModel.list.observe (this) {
            viewModel.refresh()
            adapter.shoppingList = viewModel.list.value ?: listOf()
            adapter.notifyDataSetChanged()
        }
    }
    private fun setRv() {
        adapter.shoppingList = viewModel.list.value ?: listOf()
        binding.rvShoppingList.adapter = adapter
        binding.rvShoppingList.layoutManager = LinearLayoutManager(this)
    }
    private fun setAddingItem() {
        binding.btnAddItem.setOnClickListener {
            ShoppingAdd(this) {
                    item ->
                viewModel.insertOrUpdate(item)
            }.show()
        }
    }
}
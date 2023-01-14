package com.example.shoppinglist.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.databinding.ShoppingItemBinding
import com.example.shoppinglist.recycler_view.RvShoppingAdapter.RvShoppingViewHolder
import com.example.shoppinglist.ui.shopping_list.ShoppingListViewModel

class RvShoppingAdapter (private val model: ShoppingListViewModel): RecyclerView.Adapter<RvShoppingViewHolder> () {

    private val differCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, differCallback)
    var shoppingList: List<ShoppingItem>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    inner class RvShoppingViewHolder(val binding: ShoppingItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvShoppingViewHolder {
        return RvShoppingViewHolder(
            ShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: RvShoppingViewHolder, position: Int) {
        val item = shoppingList[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvAmount.text = item.amount.toString()
        holder.binding.btnIncrease.setOnClickListener {
            changeAmount(true, item)
        }
        holder.binding.btnDecrease.setOnClickListener {
            changeAmount(false, item)
        }
        holder.binding.btnDelete.setOnClickListener {
            delete(item)
        }
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }
    private fun changeAmount(isIncrease: Boolean, item: ShoppingItem): Boolean {
        model.insertOrUpdate(
            if (isIncrease) {
                item.also { it.amount.inc() }
            } else {
                item.also { it.amount.dec() }
            }
        )
        return true
    }
    private fun delete (item: ShoppingItem): Boolean {
        model.deleteItem(item)
        return true
    }
}
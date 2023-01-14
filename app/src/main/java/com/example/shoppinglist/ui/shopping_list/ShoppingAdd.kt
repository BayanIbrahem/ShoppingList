package com.example.shoppinglist.ui.shopping_list

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.databinding.AddShoppingItemBinding

class ShoppingAdd(
    context: Context,
    val onAddShoppingItemClickListener: (item: ShoppingItem) -> Unit): AppCompatDialog(context
) {
    var toast: Toast? = null
    lateinit var binding: AddShoppingItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddShoppingItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener {
            cancel()
        }
        binding.btnSubmit.setOnClickListener {
            val item: ShoppingItem? = getItem()
            if (item != null){
                onAddShoppingItemClickListener(item)
                cancel()
            }
            else {
                toast?.cancel()
                toast = Toast.makeText(context, "sorry one or more of fields is empty", Toast.LENGTH_SHORT)
                toast?.show()
            }
        }
    }
    private fun getItem():ShoppingItem? {
        val name = binding.etName.text.toString()
        val amount = binding.etAmount.text.toString()
        return if (name.isEmpty() || amount.isEmpty()) {
            null
        } else {
            ShoppingItem(name = name, amount = amount.toInt())
        }
    }
}
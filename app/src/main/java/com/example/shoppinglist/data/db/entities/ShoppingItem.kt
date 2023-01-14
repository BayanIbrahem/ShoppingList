package com.example.shoppinglist.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "shopping_list")
data class ShoppingItem (
    @PrimaryKey(autoGenerate = true) val id: Long = -1L,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "count") var amount: Int,
)
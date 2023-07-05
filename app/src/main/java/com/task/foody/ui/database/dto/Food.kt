package com.task.foody.ui.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo("restaurant_id")
    val restaurantId: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("price")
    val price: Long,
    @ColumnInfo("photo")
    val photo: Int,
)

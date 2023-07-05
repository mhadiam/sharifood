package com.task.foody.ui.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CustomerMessageRestaurant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo("customer_id")
    val customerId: Long,
    @ColumnInfo("restaurant_id")
    val restaurantId: Long,
    @ColumnInfo("message")
    val message: String,
)

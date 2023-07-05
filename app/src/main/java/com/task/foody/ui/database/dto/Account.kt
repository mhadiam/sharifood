package com.task.foody.ui.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("userName")
    val userName: String,
    @ColumnInfo("password")
    val password: String,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("address")
    val address: String,
)

enum class AccountType {
    Restaurant,
    Customer
}

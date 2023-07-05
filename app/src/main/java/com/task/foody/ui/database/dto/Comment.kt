package com.task.foody.ui.database.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo("food_id")
    val foodId: Long,
    @ColumnInfo("comment")
    val comment: String,
    @ColumnInfo("userName")
    val userName: String,
)

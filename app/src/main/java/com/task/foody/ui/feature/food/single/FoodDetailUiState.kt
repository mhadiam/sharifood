package com.task.foody.ui.feature.food.single

import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.database.dto.Comment
import com.task.foody.ui.database.dto.Food

data class FoodDetailUiState(
    val accountType: AccountType = AccountType.Customer,
    val comments: List<Comment> = listOf(),
    val comment: String = "",
    val food: Food? = null
)
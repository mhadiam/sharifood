package com.task.foody.ui.feature.food.list

import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.database.dto.Food

data class FoodListUiState(
    val restaurantName: String = "",
    val accountType: AccountType = AccountType.Customer,
    val foods: List<Food> = listOf(),
    val message: String = "",
    val photoAddress: String = "",
    val foodName: String = "",
    val codeFree: String = "",
    val price: String = "",
)

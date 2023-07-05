package com.task.foody.ui.feature.resturant

import com.task.foody.ui.database.dto.Account


data class RestaurantListUiState(
    val restaurants: List<Account> = listOf(),
    val likedItems: List<Long> = listOf()
)

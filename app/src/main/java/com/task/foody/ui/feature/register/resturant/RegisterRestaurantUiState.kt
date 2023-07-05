package com.task.foody.ui.feature.register.resturant

data class RegisterRestaurantUiState(
    val errorMessage: String? = null,
    val name: String = "",
    val address: String = "",
    val userName: String = "",
    val password: String = "",
)

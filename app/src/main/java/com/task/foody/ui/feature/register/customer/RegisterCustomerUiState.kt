package com.task.foody.ui.feature.register.customer

data class RegisterCustomerUiState(
    val errorMessage: String? = null,
    val name: String = "",
    val userName: String = "",
    val password: String = "",
)

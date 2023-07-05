package com.task.foody.ui.feature.login

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val showError: Boolean = false
)

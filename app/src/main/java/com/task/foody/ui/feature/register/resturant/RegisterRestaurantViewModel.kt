package com.task.foody.ui.feature.register.resturant

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.task.foody.ui.ClassConstruction
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.repository.FoodyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RegisterRestaurantViewModel(application: Application) : AndroidViewModel(application) {

    var repository: FoodyRepository =
        ClassConstruction.getFoodyRepository(context = application.applicationContext)

    private val viewModelState: MutableStateFlow<RegisterRestaurantUiState> =
        MutableStateFlow(RegisterRestaurantUiState())

    val uiState by lazy {
        viewModelState
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                viewModelState.value
            )
    }

    fun updateName(name: String) {
        viewModelState.update {
            it.copy(
                name = name
            )
        }
    }

    fun updateAddress(address: String) {
        viewModelState.update {
            it.copy(
                address = address
            )
        }
    }

    fun updateUserName(userName: String) {
        viewModelState.update {
            it.copy(
                userName = userName
            )
        }
    }

    fun updatePassword(password: String) {
        viewModelState.update {
            it.copy(
                password = password
            )
        }
    }

    fun updateShowError(message: String?) {
        viewModelState.update {
            it.copy(
                errorMessage = message
            )
        }
    }


    suspend fun onRegisterClick(): Account? {
        updateShowError(null)
        val accounts = repository.getAllAccounts()
        val account = accounts.find { it.userName == viewModelState.value.userName }
        return if (account != null) {
            updateShowError("نام کاربری قبلا انتخاب شده است")
            null
        } else {
            val password = viewModelState.value.password
            if (password.length >= 4) {
                val account = Account(
                    name = viewModelState.value.name,
                    address = viewModelState.value.address,
                    userName = viewModelState.value.userName,
                    password = viewModelState.value.password,
                    type = AccountType.Restaurant.name
                )
                val id = repository.addAccount(account)

                account.copy(
                    id = id
                )
            } else {
                updateShowError("رمز عبور کوتاه است. حداقل ۴ حرف وارد کنید")
                null
            }
        }
    }

}


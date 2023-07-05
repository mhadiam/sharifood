package com.task.foody.ui.feature.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.foody.ui.ClassConstruction
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.repository.FoodyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var repository: FoodyRepository = ClassConstruction.getFoodyRepository(context = application.applicationContext)

    private val viewModelState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())

    val uiState by lazy {
        viewModelState
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                viewModelState.value
            )
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

    fun updateShowError(show: Boolean) {
        viewModelState.update {
            it.copy(
                showError = show
            )
        }
    }

    suspend fun isCorrectUserAndPass(): Account? {
        updateShowError(show = false)
        val accounts = repository.getAllAccounts()
        val account = accounts.find { it.userName == viewModelState.value.userName }
        return if (account != null){
            if (account.password == viewModelState.value.password)
                account
            else
                null
        } else {
            updateShowError(show = true)
            null
        }
    }


}
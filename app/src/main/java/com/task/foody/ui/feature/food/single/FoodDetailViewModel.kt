package com.task.foody.ui.feature.food.single

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.task.foody.ui.ClassConstruction
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.database.dto.Comment
import com.task.foody.ui.database.dto.Food
import com.task.foody.ui.pref.FoodyPref
import com.task.foody.ui.repository.FoodyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class FoodDetailViewModel(application: Application) : AndroidViewModel(application) {

    var repository: FoodyRepository =
        ClassConstruction.getFoodyRepository(context = application.applicationContext)

    private val viewModelState: MutableStateFlow<FoodDetailUiState> =
        MutableStateFlow(FoodDetailUiState())

    val userId = FoodyPref.getAccountId(application)
    lateinit var userAccount: Account
    var foodId by Delegates.notNull<Long>()
    lateinit var food: Food

    val uiState by lazy {
        viewModelState
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                viewModelState.value
            )
    }

    init {
        viewModelScope.launch {
            val accounts = repository.getAllAccounts()
            accounts.find { it.id == userId }?.let {
                userAccount = it
                viewModelState.update {
                    it.copy(
                        accountType = if (userAccount.type == AccountType.Customer.name)
                            AccountType.Customer
                        else
                            AccountType.Restaurant
                    )
                }
            }
        }
    }

    fun start(foodId: Long){
        viewModelScope.launch {
            if (foodId != 0L){
                this@FoodDetailViewModel.foodId = foodId
                val foods = repository.getFoods()

                foods.find { it.id == foodId }?.let { food ->
                    viewModelState.update {
                        it.copy(
                            food = food
                        )
                    }

                    getComments()
                }
            }
        }
    }

    private suspend fun getComments(){
        val comments = repository.getComments().filter {
            it.foodId == foodId
        }

        viewModelState.update {
            it.copy(
                comments = comments
            )
        }
    }

    fun updateComment(value: String) {
        viewModelState.update {
            it.copy(
                comment = value
            )
        }
    }

    fun submitComment() {
        viewModelScope.launch {
            val commentText = viewModelState.value.comment
            if (commentText.isNotEmpty()){
                val commentFood = Comment(
                    foodId = foodId,
                    comment = commentText,
                    userName = userAccount.name
                )
                repository.addComment(commentFood)

                viewModelState.update {
                    it.copy(
                        comment = ""
                    )
                }
                getComments()
            }
        }
    }

    fun onOrderClick() {
        // do what you want
    }

}
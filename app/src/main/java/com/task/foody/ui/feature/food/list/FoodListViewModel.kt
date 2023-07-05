package com.task.foody.ui.feature.food.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.task.foody.ui.ClassConstruction
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.database.dto.CustomerMessageRestaurant
import com.task.foody.ui.database.dto.Food
import com.task.foody.ui.pref.FoodyPref
import com.task.foody.ui.repository.FoodyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.properties.Delegates

class FoodListViewModel(application: Application) : AndroidViewModel(application) {

    var repository: FoodyRepository =
        ClassConstruction.getFoodyRepository(context = application.applicationContext)

    private val viewModelState: MutableStateFlow<FoodListUiState> =
        MutableStateFlow(FoodListUiState())

    val userId = FoodyPref.getAccountId(application)
    var restaurantId by Delegates.notNull<Long>()
    lateinit var userAccount: Account
    lateinit var restaurantAccount: Account

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

    fun start(restaurantId: Long) {
        viewModelScope.launch {
            if (restaurantId != 0L) {
                this@FoodListViewModel.restaurantId = restaurantId
                val accounts = repository.getAllAccounts()

                accounts.find { it.id == restaurantId }?.let { restaurant ->
                    restaurantAccount = restaurant

                    viewModelState.update {
                        it.copy(
                            restaurantName = restaurant.name
                        )
                    }

                    getFoods()
                }
            }
        }
    }

    private suspend fun getFoods() {

        val foods = repository.getFoods().filter {
            it.restaurantId == restaurantId
        }

        viewModelState.update {
            it.copy(
                foods = foods
            )
        }

    }

    fun updateMessage(value: String) {
        viewModelState.update {
            it.copy(
                message = value
            )
        }
    }

    fun updatePhotoAddress(value: String) {
        viewModelState.update {
            it.copy(
                photoAddress = value
            )
        }
    }

    fun updateFoodName(value: String) {
        viewModelState.update {
            it.copy(
                foodName = value
            )
        }
    }

    fun updateCodeFreeName(value: String) {
        viewModelState.update {
            it.copy(
                codeFree = value
            )
        }
    }

    fun updatePrice(value: String) {
        viewModelState.update {
            it.copy(
                price = value
            )
        }
    }

    fun onRemoveClick(id: Long) {
        viewModelScope.launch {
            repository.getFoods()
                .find { it.id == id }?.let { food ->

                    repository.deleteFood(food)

                    getFoods()
                }
        }
    }

    fun onSubmitClick() {
        viewModelScope.launch {
            if (viewModelState.value.accountType == AccountType.Customer) {
                val message = viewModelState.value.message
                val messageRestaurant = CustomerMessageRestaurant(
                    customerId = userId,
                    restaurantId = restaurantId,
                    message = message
                )

                repository.addMessageForRestaurant(messageRestaurant)

                viewModelState.update {
                    it.copy(
                        message = ""
                    )
                }
            } else {
                val food = Food(
                    restaurantId = restaurantId,
                    name = viewModelState.value.foodName,
                    price = try {
                        viewModelState.value.price.toLong()
                    } catch (e:Exception){
                        0L
                    },
                    photo = try {
                        viewModelState.value.photoAddress.toInt()
                    } catch (e:Exception){
                        0
                    }
                )
                repository.addFood(food)

                getFoods()

            }
        }
    }

}

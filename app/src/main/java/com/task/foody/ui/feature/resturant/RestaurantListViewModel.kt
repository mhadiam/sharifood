package com.task.foody.ui.feature.resturant

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.task.foody.ui.ClassConstruction
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.database.dto.CustomerLikeRestaurant
import com.task.foody.ui.feature.login.LoginUiState
import com.task.foody.ui.pref.FoodyPref
import com.task.foody.ui.repository.FoodyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RestaurantListViewModel(application: Application) : AndroidViewModel(application) {

    val repository: FoodyRepository = ClassConstruction.getFoodyRepository(context = application.applicationContext)
    val accountId = FoodyPref.getAccountId(application)
    lateinit var account: Account

    private val viewModelState: MutableStateFlow<RestaurantListUiState> = MutableStateFlow(RestaurantListUiState())

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
            accounts.find { it.id == accountId }?.let {
                account = it
            }

            getRestaurants()
            getLikedRestaurant()

        }
    }

    private suspend fun getRestaurants(){
        val restaurants = repository.getAllAccounts()
            .filter { it.type == AccountType.Restaurant.name }

        viewModelState.update {
            it.copy(
                restaurants = restaurants
            )
        }
    }

    private suspend fun getLikedRestaurant(){
        val likedItems = repository.getLikedRestaurants().filter {
            it.customerId == accountId
        }

        viewModelState.update {
            it.copy(
                likedItems = likedItems.map { it.restaurantId }
            )
        }
    }

    fun onLikeClick(id: Long) {
        viewModelScope.launch {
            val isLiked = viewModelState.value.likedItems.contains(id)

            if (isLiked){
                repository.getLikedRestaurants().filter {
                    it.customerId == accountId
                }.find { it.restaurantId == id }?.let { likedItem ->
                    repository.deleteLike(likedItem)
                }
            } else {
                repository.addLike(
                    CustomerLikeRestaurant(
                        customerId = accountId,
                        restaurantId = id
                    )
                )
            }
            getLikedRestaurant()
        }
    }

}
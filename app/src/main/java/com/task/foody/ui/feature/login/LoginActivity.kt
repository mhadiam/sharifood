package com.task.foody.ui.feature.login

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.feature.food.list.FoodListActivity
import com.task.foody.ui.feature.register.customer.RegisterCustomerActivity
import com.task.foody.ui.feature.register.resturant.RegisterRestaurantActivity
import com.task.foody.ui.feature.resturant.RestaurantListActivity
import com.task.foody.ui.pref.FoodyPref
import com.task.foody.ui.theme.FoodyTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FoodyPref.getAccountId(this) != 0L){
            FoodyPref.getAccountType(this)?.let {
                navigateToNextPage(it)
            }
        }
        setContent {
            FoodyTheme {
                val uiState by viewModel.uiState.collectAsState()

                LoginView(
                    state = uiState,
                    onUserNameChange = { userName ->
                        viewModel.updateUserName(userName = userName)
                    },
                    onPasswordChange = { password ->
                        viewModel.updatePassword(password = password)
                    },
                    onRegisterRestaurant = {
                        Intent(applicationContext, RegisterRestaurantActivity::class.java).let {
                            startActivity(it)
                        }
                    },
                    onRegisterCustomer = {
                        Intent(applicationContext, RegisterCustomerActivity::class.java).let {
                            startActivity(it)
                        }
                    },
                    onEnterClick = {
                        lifecycleScope.launch {
                            val account = viewModel.isCorrectUserAndPass()
                            if (account != null){
                                FoodyPref.saveAccountId(account.id, this@LoginActivity)
                                FoodyPref.saveAccountType(account.type, this@LoginActivity)
                                navigateToNextPage(account.type)
                            }
                        }
                    }
                )
            }
        }

    }

    private fun navigateToNextPage(type: String){
        if(type == AccountType.Customer.name) {
            Intent(this, RestaurantListActivity::class.java).let {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        } else {
            Intent(this, FoodListActivity::class.java).let {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }

}
package com.task.foody.ui.feature.register.customer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.task.foody.ui.feature.resturant.RestaurantListActivity
import com.task.foody.ui.pref.FoodyPref
import com.task.foody.ui.theme.FoodyTheme
import kotlinx.coroutines.launch

class RegisterCustomerActivity: ComponentActivity() {

    private val viewModel: RegisterCustomerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodyTheme {
                val uiState by viewModel.uiState.collectAsState()

                RegisterCustomerView(
                    state = uiState,
                    onNameChange = viewModel::updateName,
                    onUserNameChange = viewModel::updateUserName,
                    onPasswordChange = viewModel::updatePassword,
                    onRegisterClick = {
                        lifecycleScope.launch {
                            val account = viewModel.onRegisterClick()
                            if (account != null){
                                FoodyPref.saveAccountId(account.id, this@RegisterCustomerActivity)
                                FoodyPref.saveAccountType(account.type, this@RegisterCustomerActivity)
                                Intent(this@RegisterCustomerActivity, RestaurantListActivity::class.java).let {
                                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(it)
                                }
                            }
                        }
                    },
                )

            }
        }

    }

}
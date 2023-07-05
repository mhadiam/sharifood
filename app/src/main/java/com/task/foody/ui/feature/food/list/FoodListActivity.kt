package com.task.foody.ui.feature.food.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.task.foody.ui.feature.food.single.FoodDetailActivity
import com.task.foody.ui.feature.login.LoginActivity
import com.task.foody.ui.pref.FoodyPref
import com.task.foody.ui.theme.FoodyTheme

class FoodListActivity: ComponentActivity() {

    private val viewModel: FoodListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accountId = this.intent.getLongExtra("accountId", FoodyPref.getAccountId(this))
        viewModel.start(restaurantId = accountId)
        setContent {
            FoodyTheme {
                val uiState by viewModel.uiState.collectAsState()

                FoodListView(
                    state = uiState,
                    onMessageChange = viewModel::updateMessage,
                    onPhotoAddressChange = viewModel::updatePhotoAddress,
                    onFoodNameChange = viewModel::updateFoodName,
                    onCodeFreeChange = viewModel::updateCodeFreeName,
                    onPriceChange = viewModel::updatePrice,
                    onRemoveClick = viewModel::onRemoveClick,
                    onSubmitClick = viewModel::onSubmitClick,
                    onFoodClick = { foodId ->
                        Intent(this, FoodDetailActivity::class.java).let {
                            it.putExtra("foodId", foodId)
                            startActivity(it)
                        }
                    },
                    onLogoutClick = {
                        FoodyPref.saveAccountType("", this)
                        FoodyPref.saveAccountId(0L, this)
                        Intent(this, LoginActivity::class.java).let {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    }
                )
            }
        }

    }

}
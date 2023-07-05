package com.task.foody.ui.feature.food.single

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.task.foody.ui.theme.FoodyTheme

class FoodDetailActivity: ComponentActivity() {

    private val viewModel: FoodDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val foodId = this.intent.getLongExtra("foodId", 0)
        viewModel.start(foodId = foodId)
        setContent {
            FoodyTheme {
                val uiState by viewModel.uiState.collectAsState()

                FoodDetailView(
                    state = uiState,
                    onSubmitClick = viewModel::onOrderClick,
                    onSendClick = viewModel::submitComment,
                    onCommentChange = viewModel::updateComment
                )
            }
        }

    }

}
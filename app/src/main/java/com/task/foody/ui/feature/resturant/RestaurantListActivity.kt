package com.task.foody.ui.feature.resturant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.task.foody.ui.feature.food.list.FoodListActivity
import com.task.foody.ui.theme.FoodyTheme

class RestaurantListActivity : ComponentActivity() {

    private val viewModel: RestaurantListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodyTheme {
                val uiState by viewModel.uiState.collectAsState()

                RestaurantListView(
                    state = uiState,
                    onRestaurantClick = { restaurantId ->
                        Intent(this, FoodListActivity::class.java).let {
                            it.putExtra("accountId", restaurantId)
                            startActivity(it)
                        }
                    },
                    onLikeClick = viewModel::onLikeClick
                )
            }
        }

    }


}
package com.task.foody.ui.feature.resturant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.foody.R
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.feature.food.list.clickableWithoutRipple
import com.task.foody.ui.theme.FoodyTheme
import com.task.foody.ui.theme.Gray

@Preview
@Composable
fun RestaurantListViewPreview() {
    FoodyTheme {
        RestaurantListView(
            state = RestaurantListUiState(
                restaurants = listOf(
                    Account(
                        id = 1,
                        name = "اسم",
                        userName = "userName",
                        password = "aaaa",
                        type = AccountType.Restaurant.name,
                        address = "آدرس"
                    )
                )
            ),
            onRestaurantClick = {},
            onLikeClick = {}
        )
    }
}

@Composable
fun RestaurantListView(
    state: RestaurantListUiState,
    onRestaurantClick: (Long) -> Unit,
    onLikeClick: (Long) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        items(state.restaurants) { item ->

            RestaurantItem(
                name = item.name,
                address = item.address,
                isLiked = state.likedItems.contains(item.id),
                onRestaurantClick = {
                    onRestaurantClick(item.id)
                },
                onLikeClick = {
                    onLikeClick(item.id)
                }
            )

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Gray))
        }


        item {
            Spacer(modifier = Modifier.height(200.dp))
        }

    }

}

@Composable
fun RestaurantItem(
    name: String,
    address: String,
    isLiked: Boolean,
    onRestaurantClick: () -> Unit,
    onLikeClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRestaurantClick() }
            .padding(horizontal = 32.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            modifier = Modifier.size(24.dp)
                .clickableWithoutRipple { onLikeClick() },
            painter = painterResource(
                id =
                if (isLiked)
                    R.drawable.ic_like
                else
                    R.drawable.ic_not_like
            ),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))


        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = address,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis
            )

        }

    }


}
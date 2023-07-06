package com.task.foody.ui.feature.food.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.task.foody.R
import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.database.dto.Food
import com.task.foody.ui.feature.login.FoodyInputView
import com.task.foody.ui.theme.FoodyTheme
import com.task.foody.ui.theme.defaultTextStyle
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Preview
@Composable
fun FoodListViewPreview() {

    FoodyTheme {
        FoodListView(
            state = FoodListUiState(
                accountType = AccountType.Restaurant,
                foods = listOf(
                    Food(
                        id = 0L,
                        restaurantId = 0L,
                        name = "همبرگر",
                        price = 100000L,
                        photo = 1,

                        )
                )
            ),
            onMessageChange = {},
            onPhotoAddressChange = {},
            onFoodNameChange = {},
            onCodeFreeChange = {},
            onPriceChange = {},
            onSubmitClick = {},
            onRemoveClick = {},
            onFoodClick = {},
            onLogoutClick = {}
        )
    }

}

@Composable
fun FoodListView(
    state: FoodListUiState,
    onMessageChange: (String) -> Unit,
    onPhotoAddressChange: (String) -> Unit,
    onFoodNameChange: (String) -> Unit,
    onCodeFreeChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onRemoveClick: (Long) -> Unit,
    onFoodClick: (Long) -> Unit,
    onLogoutClick: () -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            if (state.accountType == AccountType.Customer) {
                FoodCustomerInput(
                    message = state.message,
                    onMessageChange = onMessageChange,
                    restaurantName = state.restaurantName
                )
            } else {
                FoodRestaurantInput(
                    state = state,
                    onPhotoAddressChange = onPhotoAddressChange,
                    onFoodNameChange = onFoodNameChange,
                    onCodeFreeChange = onCodeFreeChange,
                    onPriceChange = onPriceChange,
                )
            }


            Spacer(modifier = Modifier.height(24.dp))


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { onSubmitClick() }
                    .padding(vertical = 8.dp)
                    ,
                text = if (state.accountType == AccountType.Customer) "ارسال" else "افزودن به منوی ${state.restaurantName}",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                items(state.foods) { item ->

                    FoodItem(
                        name = item.name,
                        price = item.price,
                        photo = item.photo,
                        accountType = state.accountType,
                        onRemoveClick = {
                            onRemoveClick(item.id)
                        },
                        onFoodClick = {
                            onFoodClick(item.id)
                        }
                    )

                }


                item {
                    Spacer(modifier = Modifier.height(200.dp))
                }

            }

        }


        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clickableWithoutRipple { onLogoutClick() }
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier,
                text = "خروج",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.background,
                textAlign = TextAlign.Center
            )

        }

    }


}

@Composable
fun FoodCustomerInput(
    message: String,
    onMessageChange: (String) -> Unit,
    restaurantName: String,
) {
    FoodyInputView(
        value = message,
        onValueChange = onMessageChange,
        placeHolder = "پیام به $restaurantName"
    )
}

@Composable
fun FoodRestaurantInput(
    state: FoodListUiState,
    onPhotoAddressChange: (String) -> Unit,
    onFoodNameChange: (String) -> Unit,
    onCodeFreeChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            FoodyInputView(
                modifier = Modifier.weight(1f),
                value = state.photoAddress,
                onValueChange = onPhotoAddressChange,
                placeHolder = "آدرس عکس"
            )

            Spacer(modifier = Modifier.width(16.dp))

            FoodyInputView(
                modifier = Modifier.weight(1f),
                value = state.foodName,
                onValueChange = onFoodNameChange,
                placeHolder = "نام غذا"
            )

        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            FoodyInputView(
                modifier = Modifier.weight(1f),
                value = state.codeFree,
                onValueChange = onCodeFreeChange,
                placeHolder = "کد تخفیف"
            )

            Spacer(modifier = Modifier.width(16.dp))

            FoodyInputView(
                modifier = Modifier.weight(1f),
                value = state.price,
                onValueChange = onPriceChange,
                placeHolder = "قیمت به تومان"
            )

        }

    }

}

@Composable
fun FoodItem(
    name: String,
    photo: Int,
    price: Long,
    accountType: AccountType,
    onRemoveClick: () -> Unit,
    onFoodClick: () -> Unit,
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.05f)
            .padding(vertical = 12.dp)
            .clickable { onFoodClick() }
    ) {

        val (nameView, priceView, image, button, background) = createRefs()


        Image(
            modifier = Modifier.constrainAs(background) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
                .clip(shape = RoundedCornerShape(4.dp)),
            painter = painterResource(
                id = if (isSystemInDarkTheme())
                    R.drawable.background_dark_food
                else
                    R.drawable.background_light_food
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Image(
            modifier = Modifier
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.3f)
                }
                .size(70.dp),
            painter = painterResource(id = getFoodImageWithNumber(photo)),
            contentDescription = null,
        )

        if (accountType == AccountType.Restaurant) {

            Text(
                modifier = Modifier
                    .constrainAs(button) {
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    }
                    .clickable { onRemoveClick() }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                text = "حذف",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp
            )
        }


        Text(
            modifier = Modifier
                .constrainAs(priceView) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
                .padding(16.dp),
            text = "${price.toString().separateNumber()} تومان",
            style = defaultTextStyle.copy(textDirection = TextDirection.Rtl),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            modifier = Modifier
                .constrainAs(nameView) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(16.dp),
            text = name,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )


    }

}

fun getFoodImageWithNumber(number: Int): Int {
    return when (number) {
        1 -> R.drawable.burger
        2 -> R.drawable.chicken
        3 -> R.drawable.french_fries
        4 -> R.drawable.fried_chicken
        5 -> R.drawable.fried_egg
        6 -> R.drawable.hot_dog
        7 -> R.drawable.japanese_food
        8 -> R.drawable.kebab
        9 -> R.drawable.noodles
        10 -> R.drawable.pizza
        11 -> R.drawable.shish_kebab
        12 -> R.drawable.spaghetti
        else -> R.drawable.burger
    }
}

fun String.separateNumber(): String {
    return try {
        if (this.contains(",")) {
            this.replace((",".toRegex()), "")
        }
        val longVal: Long = this.toLong()
        val formatter: DecimalFormat =
            NumberFormat.getInstance(Locale.US) as DecimalFormat
        formatter.applyPattern("#,###,###,###")
        val formattedString: String = formatter.format(longVal)
        formattedString
    } catch (nfe: NumberFormatException) {
        nfe.printStackTrace()
        ""
    }
}

fun Modifier.clickableWithoutRipple(onClick: () -> Unit) = composed {
    val interactionSource = remember { MutableInteractionSource() }
    this.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = onClick
    )
}
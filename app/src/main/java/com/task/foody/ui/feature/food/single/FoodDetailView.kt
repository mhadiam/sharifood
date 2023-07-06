package com.task.foody.ui.feature.food.single

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.task.foody.R
import com.task.foody.ui.database.dto.AccountType
import com.task.foody.ui.database.dto.Comment
import com.task.foody.ui.database.dto.Food
import com.task.foody.ui.feature.food.list.getFoodImageWithNumber
import com.task.foody.ui.feature.food.list.separateNumber
import com.task.foody.ui.feature.login.FoodyInputView
import com.task.foody.ui.theme.FoodyTheme


@Preview
@Composable
fun FoodDetailViewPreview() {
    FoodyTheme {
        FoodDetailView(
            state = FoodDetailUiState(
                food = Food(
                    id = 0L,
                    restaurantId = 0L,
                    name = "همبرگر",
                    price = 100000L,
                    photo = 1,
                ),
                comments = listOf(
                    Comment(
                        id = 0L,
                        foodId = 0L,
                        comment = "سلام",
                        userName = "مهدی"
                    ),                    Comment(
                        id = 1L,
                        foodId = 0L,
                        comment = "سلام",
                        userName = "مهدی"
                    ),
                )
            ),
            onSubmitClick = {},
            onSendClick = {},
            onCommentChange = {}
        )
    }
}

@Composable
fun FoodDetailView(
    state: FoodDetailUiState,
    onSubmitClick: () -> Unit,
    onSendClick: () -> Unit,
    onCommentChange: (String) -> Unit
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (topBar, bottomView, button, commentInput) = createRefs()

        FoodDetailTopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
                .aspectRatio(2.05f),
            name = state.food?.name ?: "",
            photo = state.food?.photo ?: 1,
            price = state.food?.price ?: 0L
        )

        FoodDetailComments(
            modifier = Modifier.constrainAs(bottomView) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(topBar.bottom)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            comments = state.comments
        )

        if (state.accountType == AccountType.Customer) {
            FoodDetailOrderButton(
                modifier = Modifier.constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topBar.bottom)
                    bottom.linkTo(topBar.bottom)
                    width = Dimension.fillToConstraints
                },
                onSubmitClick = onSubmitClick
            )
        }

        CommentInput(
            modifier = Modifier.constrainAs(commentInput) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            comment = state.comment,
            onCommentChange = onCommentChange,
            onSendClick = onSendClick
        )

    }

}

@Composable
fun FoodDetailTopBar(
    modifier: Modifier = Modifier,
    name: String,
    photo: Int,
    price: Long,
) {

    ConstraintLayout(modifier = modifier) {

        val (background, foodName, foodPrice, image) = createRefs()

        Image(
            modifier = Modifier.constrainAs(background) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
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
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
                .size(100.dp),
            painter = painterResource(id = getFoodImageWithNumber(photo)),
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .constrainAs(foodPrice) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
                .padding(16.dp),
            text = "${price.toString().separateNumber()} تومان",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            modifier = Modifier
                .constrainAs(foodName) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
                .padding(16.dp),
            text = name,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }

}

@Composable
fun FoodDetailComments(
    modifier: Modifier = Modifier,
    comments: List<Comment>
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp)
            .padding(top = 40.dp)
    ) {

        items(comments) { item ->

            CommentItem(
                userName = item.userName,
                comment = item.comment
            )

        }

        item {
            Spacer(modifier = Modifier.height(200.dp))
        }

    }

}

@Composable
fun FoodDetailOrderButton(
    modifier: Modifier = Modifier,
    onSubmitClick: () -> Unit
) {

    Text(
        modifier = modifier
            .padding(horizontal = 32.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onSubmitClick() }
            .padding(vertical = 8.dp),
        text = "سفارش",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.background,
        textAlign = TextAlign.Center
    )

}

@Composable
fun CommentInput(
    modifier: Modifier = Modifier,
    comment: String,
    onCommentChange: (String) -> Unit,
    onSendClick: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .background(
                color = if (isSystemInDarkTheme()) Color(0xFF292E31) else Color(0xFFDAD4D1)
            )
            .padding(horizontal = 32.dp, vertical = 10.dp)
    ) {

        val (button, input) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RectangleShape
                )
                .clickable { onSendClick() }
                .padding(vertical = 16.dp, horizontal = 24.dp)
                ,
            text = "ارسال",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        FoodyInputView(
            modifier = Modifier.constrainAs(input) {
                start.linkTo(button.end)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
                width = Dimension.fillToConstraints
//                height = Dimension.fillToConstraints
            },
            value = comment,
            onValueChange = onCommentChange,
            placeHolder = "نظر شما",
            fontSize = 16.sp
        )

    }

}

@Composable
fun CommentItem(
    userName: String,
    comment: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = userName,
            fontSize = 16.sp,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = comment,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Right,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}
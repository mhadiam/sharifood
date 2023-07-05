package com.task.foody.ui.feature.register.resturant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.foody.ui.feature.login.FoodyInputView
import com.task.foody.ui.feature.login.RegisterButton
import com.task.foody.ui.theme.FoodyTheme
import com.task.foody.ui.theme.Gray

@Preview
@Composable
fun RegisterRestaurantViewPreview() {

    FoodyTheme {
        RegisterRestaurantView(
            state = RegisterRestaurantUiState(),
            onNameChange = {},
            onAddressChange = {},
            onUserNameChange = {},
            onPasswordChange = {},
            onRegisterClick = {},
        )
    }

}

@Composable
fun RegisterRestaurantView(
    state: RegisterRestaurantUiState,
    onNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "ثبت رستوران", fontSize = 36.sp, color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = state.errorMessage ?: "",
            fontSize = 16.sp,
            color = if (state.errorMessage != null) MaterialTheme.colorScheme.primary else Color.Transparent
        )

        Spacer(modifier = Modifier.height(24.dp))

        FoodyInputView(
            value = state.name, onValueChange = onNameChange, placeHolder = "نام رستوران"
        )

        Spacer(modifier = Modifier.height(24.dp))

        FoodyInputView(
            value = state.address, onValueChange = onAddressChange, placeHolder = "آدرس"
        )

        Spacer(modifier = Modifier.height(24.dp))

        FoodyInputView(
            value = state.userName, onValueChange = onUserNameChange, placeHolder = "نام کاربری"
        )

        Spacer(modifier = Modifier.height(24.dp))

        FoodyInputView(
            value = state.password, onValueChange = onPasswordChange, placeHolder = "رمز عبور"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(4.dp)
                )
                .clickable { onRegisterClick() }
                .padding(vertical = 8.dp)
                ,
            text = "ثبت",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.background,
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "شریف فود",
            fontSize = 14.sp,
            color = Gray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))


    }

}
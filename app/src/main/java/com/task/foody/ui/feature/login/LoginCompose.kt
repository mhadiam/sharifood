package com.task.foody.ui.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.foody.ui.theme.*

@Preview
@Composable
fun LoginViewPreview() {
    FoodyTheme {
        LoginView(
            onUserNameChange = { s -> },
            onPasswordChange = { s -> },
            onRegisterRestaurant = {},
            onRegisterCustomer = {},
            onEnterClick = {},
            state = LoginUiState()
        )
    }
}

@Composable
fun LoginView(
    state: LoginUiState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegisterRestaurant: () -> Unit,
    onRegisterCustomer: () -> Unit,
    onEnterClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "ورود",
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "اطلاعات اشتباه است",
            fontSize = 16.sp,
            color = if (state.showError) MaterialTheme.colorScheme.primary else Color.Transparent
        )

        Spacer(modifier = Modifier.height(24.dp))

        FoodyInputView(
            value = state.userName,
            onValueChange = onUserNameChange,
            placeHolder = "نام کاربری"
        )

        Spacer(modifier = Modifier.height(24.dp))

        FoodyInputView(
            value = state.password,
            onValueChange = onPasswordChange,
            placeHolder = "رمز عبور"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { onEnterClick() }
                .padding(vertical = 8.dp)
                ,
            text = "ورود",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.background,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(64.dp))


        RegisterButton(
            title = "ثبت رستوران",
            onClick = onRegisterRestaurant
        )

        Spacer(modifier = Modifier.height(24.dp))


        RegisterButton(
            title = "ثبت مشتری",
            onClick = onRegisterCustomer
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodyInputView(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    fontSize: TextUnit = 20.sp
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeHolder,
                    fontSize = fontSize,
                    color = Gray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Transparent
            ),
            singleLine = true,
            textStyle = defaultTextStyle.copy(fontSize=fontSize)
        )
    }

}


@Composable
fun RegisterButton(
    title: String,
    onClick: () -> Unit
) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSystemInDarkTheme()) DarkDarkerBackground else LightLighterBackground,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 8.dp)
            ,
        text = title,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )

}

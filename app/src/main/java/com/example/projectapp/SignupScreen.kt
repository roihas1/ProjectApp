package com.example.projectapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.theme.ProjectAppTheme

@Composable
fun SignUpScreen(modifier: Modifier= Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        Title()
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Sign up",
                color = Color.White,
                fontSize = 36.sp
            )
            Text(
                text = "Create new account",
                color = Color.White,
                fontSize = 24.sp
            )
        }
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                text = "Username",
                onValueChange = {/*TODO*/},
                icon = Icons.Default.Person
            )
            CustomTextField(
                text = "Email",
                onValueChange = {/*TODO*/},
                icon = Icons.Default.Person
            )
            CustomTextField(
                text = "Password",
                onValueChange = {/*TODO*/},
                icon = Icons.Default.Lock
            )
            CustomTextField(
                text = "Confirm Password",
                onValueChange = {/*TODO*/},
                icon = Icons.Default.Lock
            )
        }
        FunctionButton(
            text = "Sign up",
            onClick = {/*TODO*/}
        )






    }


}





@Preview
@Composable
fun PreviewSignUpScreen() {
    ProjectAppTheme {
        SignUpScreen()
    }
}
package com.example.projectapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme


@Composable
fun SignUpScreen(navController: NavController, modifier: Modifier= Modifier) {

    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }


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
                value = username,
                onValueChange = {newUsername -> username = newUsername},
                icon = Icons.Default.Person,
                label = "Username"

            )
            CustomTextField(
                label = "Email",
                onValueChange = {newEmail -> email = newEmail},
                icon = Icons.Default.Person,
                value = email
            )
            CustomTextField(
                label = "Password",
                onValueChange = {newPassword -> password = newPassword},
                icon = Icons.Default.Lock,
                isPassword = true,
                value = password
            )
            CustomTextField(
                label = "Confirm Password",
                onValueChange = {newConfirmPassword -> confirmPassword = newConfirmPassword},
                icon = Icons.Default.Lock,
                isPassword = true,
                value = confirmPassword
            )
        }
        FunctionButton(
            text = "Sign up",
            onClick = {navController.navigate("HomeScreen")}
        )
        
    }


}


@Preview
@Composable
fun PreviewSignUpScreen() {
    ProjectAppTheme {
        val navController = rememberNavController()
        SignUpScreen(navController)
    }
}


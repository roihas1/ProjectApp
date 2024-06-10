package com.example.projectapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme
import com.example.projectapp.viewmodel.AuthViewModel
import com.example.projectapp.viewmodel.SignUpState


@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel , modifier: Modifier= Modifier) {
    val signUpState = viewModel.signUpState
    val context = LocalContext.current

    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant)))
            .verticalScroll(rememberScrollState()),
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
                value = viewModel.firstName,
                onValueChange = {viewModel.firstName = it},
                icon = Icons.Default.Person,
                label = "First Name"
            )
            CustomTextField(
                value = viewModel.lastName,
                onValueChange = {viewModel.lastName = it},
                icon = Icons.Default.Person,
                label = "Last Name"
            )
            CustomTextField(
                label = "Phone number",
                onValueChange = {viewModel.phoneNumber = it},
                icon = Icons.Default.Person,
                value = viewModel.phoneNumber
            )
            CustomTextField(
                label = "Email",
                onValueChange = {viewModel.email = it},
                icon = Icons.Default.Person,
                value = viewModel.email
            )
            CustomTextField(
                label = "Password",
                onValueChange = {viewModel.password = it},
                icon = Icons.Default.Lock,
                isPassword = true,
                value = viewModel.password
            )
            CustomTextField(
                label = "Confirm Password",
                onValueChange = {viewModel.rePassword = it},
                icon = Icons.Default.Lock,
                isPassword = true,
                value = viewModel.rePassword
            )
        }

        Row {
            FunctionButton(
                modifier.padding(vertical = 120.dp),
                onClick = {
                   viewModel.resetState()
                          },
                text = "Clear",
                buttonWidth = 110.dp,
                enabledBackgroundColors = listOf(MyColors.ButtonColor,MyColors.Secondary)
            )
            FunctionButton(
                modifier = modifier.padding(vertical = 120.dp), // for allow scrolling and reach the confirm password field. ,
                text = "Sign up",
                onClick = {
//                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    val passwordPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}\$"
//                    else if (!viewModel.email.matches(emailPattern.toRegex())) {
//                    Toast.makeText(context, "Invalid email format(client)", Toast.LENGTH_SHORT).show()
//                }
                    if (viewModel.firstName.isEmpty() || viewModel.lastName.isEmpty()|| viewModel.email.isEmpty() || viewModel.password.isEmpty() || viewModel.rePassword.isEmpty()) {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT)
                            .show()
                    }  else if (viewModel.password != viewModel.rePassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else if (!viewModel.password.matches(passwordPattern.toRegex())) {
                        Toast.makeText(
                            context,
                            "Password must contain at least one capital letter and one number, and be at least 8 characters long",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(context, "enter to api func", Toast.LENGTH_SHORT).show()
                        viewModel.signUp(navController)
//                        navController.navigate("HomeScreen")
                    }
                }
            )
        }
        when (signUpState) {
            is SignUpState.Loading -> {

            }
            is SignUpState.Success -> {
//                navController.navigate("HomeScreen")
                Text("Sign-Up Successful!")
            }
            is SignUpState.Error -> {

                Text("Error: ${signUpState.message}")
            }
            else -> {}
        }
        
    }


}


@Preview
@Composable
fun PreviewSignUpScreen() {
    ProjectAppTheme {
        val navController = rememberNavController()
        val viewModel = AuthViewModel()
        SignUpScreen(navController,viewModel)
    }
}


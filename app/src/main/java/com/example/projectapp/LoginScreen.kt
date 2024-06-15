package com.example.projectapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme
import com.example.projectapp.viewmodel.AuthViewModel
import com.example.projectapp.viewmodel.LoginState
import com.example.projectapp.viewmodel.SignUpState


@Composable
fun CustomTextField(
    modifier: Modifier=Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    isPassword: Boolean = false
) {

    var passwordVisibility by remember { mutableStateOf(false)}


    val iconVisibility = if (passwordVisibility)
        painterResource(id =R.drawable.visibility)
    else
        painterResource(id = R.drawable.visibility_off)

    if (isPassword){
        TextField(

            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
            shape = MaterialTheme.shapes.large,
            visualTransformation= if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = {passwordVisibility = !passwordVisibility},
                ){
                    Icon(painter = iconVisibility,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(12.dp)
                            .size(24.dp)
                        )
                }
            },

        )

    }else {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
            shape = MaterialTheme.shapes.large,
        )
    }
    
}
@Composable
fun LoginScreen(navController: NavController,
                viewModel: AuthViewModel,
                modifier: Modifier=Modifier
) {
    var loginState = viewModel.loginState
    var password by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var showErrorDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Title()
        Text(
            text = "Login to continue",
            color = Color.White,
            fontSize = 24.sp
        )
        CustomTextField(
            value = viewModel.email,
            onValueChange = {viewModel.email = it},
            icon = Icons.Default.Person,
            label = "Username"
        )
        CustomTextField(
            value = viewModel.password,
            onValueChange = {viewModel.password = it},
            icon = Icons.Default.Lock,
            isPassword = true,
            label = "Password"
        )
        if (showErrorDialog){
            Text(text = "Wrong username or password. Please try again.") // todo change it to mutable variable and design it
        }

        FunctionButton(
            text = "Login",
            onClick = {
                if (viewModel.email.isEmpty() || viewModel.password.isEmpty()){
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT)
                        .show()
                }
                else {
//                    navController.navigate("HomeScreen")
                    viewModel.login(navController)
                }
//                navController.navigate("HomeScreen")
            }
        )
        Spacer(modifier = modifier.height(48.dp))
        FooterCreateAccount(modifier,navController,viewModel)
    }
//    if (showErrorDialog) {
//        AlertDialog(
//            onDismissRequest = { showErrorDialog = false },
//            title = { Text(text = "Error") },
//            text = { Text(text = "Wrong username or password. Please try again.") },
//            confirmButton = {
//                Button(onClick = {
////                    loginState = LoginState.Loading
//                    showErrorDialog = false
//                }) {
//                    Text("OK")
//                }
//            }
//        )
//    }
    when (loginState) {
        is LoginState.Loading -> {

        }
        is LoginState.Success -> {
//                navController.navigate("HomeScreen")
            Text("Login Successful!")
        }
        is LoginState.Error -> {
            showErrorDialog = true
//            Toast.makeText(context, "wrong password/username", Toast.LENGTH_LONG)
//                .show()
//            Text("Error: ${loginState.message}")
        }
        else -> {}
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        val viewModel = AuthViewModel()
        LoginScreen(navController,viewModel)
    }

}
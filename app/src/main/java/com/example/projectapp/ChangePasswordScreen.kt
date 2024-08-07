package com.example.projectapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun ChangePasswordScreen(navController: NavController,modifier: Modifier = Modifier){

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MyColors.Primary,
                        MyColors.PrimaryVariant
                    )
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title()
        Text(
            text = "Change your password",
            style = TextStyle(fontSize = 24.sp),
            color = Color.White
        )
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                value = "",
                onValueChange = {},
                icon = Icons.Default.Person,
                label = "Password"

            )
            CustomTextField(
                label = "New Password",
                onValueChange = {},
                icon = Icons.Default.Person,
                value = "",
                modifier = Modifier.padding(vertical = 16.dp)

            )
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            FunctionButton(
                onClick =
                {
                    Toast.makeText(context, "Password has changed", Toast.LENGTH_SHORT).show()
                    coroutineScope.launch {
                        delay(2500)
                        navController.navigate("ProfileScreen")
                }
                },
                text = "Submit"
            )

        }
        BottomNavigation(navController = navController)
    }



}









@Preview
@Composable
fun ChangePasswordScreenPreview(){
    ProjectAppTheme {
        val navController = rememberNavController()
        ChangePasswordScreen(navController)
    }


}
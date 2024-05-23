package com.example.projectapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme

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
    ) {
        Text(
            text = "Change your password",
            modifier = modifier
                .padding(16.dp),
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
                value = ""

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
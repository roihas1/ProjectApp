package com.example.projectapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.theme.ProjectAppTheme

@Composable
fun LoginScreen(
    modifier: Modifier=Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyColors.Primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Title()
        Text(
            text = "Sign in to continue",
            color = Color.White,
            fontSize = 24.sp
        )
        TextField(
            value ="" ,
            onValueChange = {/*TODO*/},
        )
        TextField(
            value ="" ,
            onValueChange = {/*TODO*/}
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ProjectAppTheme {
        LoginScreen()
    }

}
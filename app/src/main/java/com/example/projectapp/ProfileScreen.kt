package com.example.projectapp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme

@Composable
fun ProfileScreen(navController: NavController,modifier: Modifier = Modifier){
   
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                FunctionButton(
                    modifier = Modifier,
                    onClick = { navController.navigate("welcomeScreen") },// todo insert logout functionality
                    text = "Logout",
                    buttonWidth = 120.dp,
                    textSize = 16.sp
                )
                Icon(
                    Icons.Rounded.Settings,
                    contentDescription = "Settings",
                    Modifier
                        .padding(horizontal = 18.dp)
                        .size(48.dp)
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = {/*todo*/}),
                    tint = Color.White,
                    )
            }
            Row(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                horizontalArrangement = Arrangement.Center
            ) {

                Column(
                    modifier= modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_missing),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                    )
                    FunctionButton(
                        modifier = Modifier,
                        onClick = { /*todo edit*/ },
                        text = "Edit",
                        buttonWidth = 80.dp,
                        textSize = 16.sp
                    )
                }
            }
            Text(
                text = "User Name",
                modifier = modifier
                    .padding(16.dp),
                style = TextStyle(fontSize = 24.sp),
                color = Color.White
            )
            Text(
                text = "Email",
                modifier = modifier
                    .padding(16.dp),
                style = TextStyle(fontSize = 24.sp),
                color = Color.White
            )
            FunctionButton(
                modifier = Modifier,
                onClick = { navController.navigate("changePassword") },
                text = "Change password",
                buttonWidth = 360.dp,

            )
            FunctionButton(
                modifier = Modifier,
                onClick = { navController.navigate("question1") },
                text = "New Investment Portfolio",
                buttonWidth = 360.dp,

            )

            BottomNavigation(navController, modifier)
        }
    }




@Preview
@Composable
fun ProfileScreenPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        ProfileScreen(navController)
    }

}
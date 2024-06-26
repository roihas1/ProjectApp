package com.example.projectapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme


@Composable
fun HomeScreen(navController: NavController,
               modifier: Modifier = Modifier
) {

    Column ( modifier = modifier
        .fillMaxSize()
        .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween,

    ){
        Title()
        FunctionButton(
            onClick = { navController.navigate("question1")},
            text ="Create New\n\nInvestment\n\n  Portfolio",
            buttonWidth = 320.dp,
            contentPadding = PaddingValues(30.dp),
            textSize = 32.sp
        )
        FunctionButton(
            onClick = { },
            text ="Go To Last\n\n Portfolio",
            buttonWidth = 320.dp,
            contentPadding = PaddingValues(48.dp),
            textSize = 32.sp
        )
        BottomNavigation(navController,modifier,true)

    }

}

@Composable
fun BottomNavigation(navController: NavController,modifier: Modifier=Modifier,selectedHome:Boolean =false,selectedProfile:Boolean=false,selectedMyPortfolios:Boolean=false) {
    NavigationBar(
        modifier=modifier,
        containerColor = MyColors.Primary,
        contentColor = Color.White,
        ) {
        NavigationBarItem(
            selected = selectedHome,
            onClick = { navController.navigate("HomeScreen") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            ),
            modifier = if (selectedHome)  Modifier.background(Color.White.copy(alpha = 0.1f))
                        else Modifier
            )
        NavigationBarItem(
            selected = selectedProfile,
            onClick = { navController.navigate("ProfileScreen") },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            ),
            modifier = if (selectedProfile)  Modifier.background(Color.White.copy(alpha = 0.1f))
            else Modifier
        )
        NavigationBarItem(
            selected = selectedMyPortfolios,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("My portfolios") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            ),
            modifier = if (selectedMyPortfolios)  Modifier.background(Color.White.copy(alpha = 0.1f))
            else Modifier
        )



    }
    
}


@Preview
@Composable
fun HomeScreenPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }

}
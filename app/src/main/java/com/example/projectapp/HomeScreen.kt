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
       Button(
                onClick = { navController.navigate("question1") },
                modifier = Modifier
                    .padding(24.dp)
                    .width(320.dp),
                shape = MaterialTheme.shapes.extraLarge,
                contentPadding = PaddingValues(40.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MyColors.ButtonColor,
                    containerColor = MyColors.ButtonColor
                )
            ) {
                Text(
                    modifier= Modifier.padding(16.dp),
                    text="Create New\n\nInvestment\n\nPortfolio",
                    fontSize = 32.sp,
                    color= Color.White,
                    textAlign = TextAlign.Center
                )
            }

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(16.dp)
                    .width(320.dp),
                shape = MaterialTheme.shapes.large,
                contentPadding = PaddingValues(48.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MyColors.ButtonColor,
                    containerColor = MyColors.ButtonColor
                )
            ) {
                Text(
                    modifier= Modifier.padding(18.dp),
                    text="Go To Last\n\n Portfolio",
                    fontSize = 32.sp,
                    color= Color.White,
                    textAlign = TextAlign.Center
                )
            }
//        Spacer(modifier =modifier.height(0.dp))
        BottomNavigation(navController,modifier)


    }

}

@Composable
fun BottomNavigation(navController: NavController,modifier: Modifier=Modifier) {
    NavigationBar(
        modifier=modifier,
        containerColor = MyColors.Primary,
        contentColor = Color.White,
        ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("HomeScreen") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )

            )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("ProfileScreen") },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("My portfolios") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )



    }
    
}
//@Preview
//@Composable
//fun BottomNavigationPreview() {
//    ProjectAppTheme {
//        BottomNavigation()
//    }
//}

@Preview
@Composable
fun HomeScreenPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }

}
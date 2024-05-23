package com.example.projectapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcomeScreen") {
        composable("welcomeScreen"){
            WelcomeScreen(navController)
        }
        composable("loginScreen"){
            LoginScreen(navController)
        }
        composable("SignupScreen"){
            SignUpScreen(navController)
        }
        composable("HomeScreen"){
            HomeScreen(navController)
        }
        composable("ProfileScreen"){
            ProfileScreen(navController)
        }
        composable("changePassword"){
            ChangePasswordScreen(navController)
        }
    }
}

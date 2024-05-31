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
        composable("question1") { SurveyScreen(navController, 1, "What is your name?",listOf("Apple", "Banana", "Cherry")) }
        composable("question2") { SurveyScreen(navController, 2, "How old are you?",listOf("Apple", "Banana", "Cherry","Watermelon")) }
        composable("question3") { SurveyScreen(navController, 3, "What is your favorite color?",listOf("Apple", "Banana", "Cherry","Grape")) }
        composable("question4") { SurveyScreen(navController, 4, "What is your favorite hobby?",listOf("Apple", "Banana", "Cherry")) }
        composable("question5") { SurveyScreen(navController, 5, "What is your favorite food?",listOf("Apple", "Banana", "Cherry","Skoda")) }
        composable("question6") { SurveyScreen(navController, 6, "Where do you live?",listOf("Apple", "Banana", "Cherry")) }
        composable("summary") { SummaryScreen(navController) }
    }
}

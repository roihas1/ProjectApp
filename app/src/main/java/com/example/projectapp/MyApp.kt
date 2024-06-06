package com.example.projectapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.projectapp.viewmodel.AuthViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel = AuthViewModel()
    NavHost(navController = navController, startDestination = "welcomeScreen") {
        composable("welcomeScreen"){
            WelcomeScreen(navController)
        }
        composable("loginScreen"){
            LoginScreen(navController)
        }
        composable("SignupScreen"){
            SignUpScreen(navController,viewModel)
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
        composable("question1") {
            SurveyScreen(navController, 1, "Do you want to use machine learning?",listOf("No", "Yes"))
        }
        composable("question2") {
            SurveyScreen(navController, 2, "Choose model",listOf("Markowitz","Gini"))
        }
        composable("question3") {
            SurveyScreen(navController, 3, "Choose your collection",listOf("Indexes(recommended)", "Top indexes", "Indexes and stocks","Top stocks"))
        }
        composable("question4") {
            SurveyScreen(navController, 4, "For how many years do you want to invest?",listOf("0-2", "2-4", "4-100"))
        }
        composable("question5") {
            SurveyScreen(navController, 5, "What level of risk do you prefer?",listOf("Low", "Medium", "High"))
        }
        composable("question6") {
            SurveyScreen(navController, 6, "Which graph do you prefer?",listOf("Safest", "Sharpe", "Max return"))
        }
        composable("summary") {
            SummaryScreen(navController)
        }
    }
}

package com.example.projectapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.projectapp.viewmodel.AuthViewModel
import com.example.projectapp.viewmodel.SurveyViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    val surveyViewModel = SurveyViewModel()
    NavHost(navController = navController, startDestination = "welcomeScreen") {
        composable("welcomeScreen"){
            WelcomeScreen(navController,authViewModel)
        }
        composable("loginScreen"){
            LoginScreen(navController,authViewModel)
        }
        composable("SignupScreen"){
            SignUpScreen(navController,authViewModel)
        }
        composable("HomeScreen"){
            HomeScreen(navController)
        }
        composable("ProfileScreen"){
            ProfileScreen(navController,authViewModel)
        }
        composable("changePassword"){
            ChangePasswordScreen(navController)
        }
        composable("question1") {
            SurveyScreen(navController, surveyViewModel,1, "The amount I will deposit into my portfolio in shekels is:",listOf())
        }
        composable("question2") {
            SurveyScreen(navController, surveyViewModel,2, "Do you want to use machine learning?",listOf("No", "Yes"))
        }
        composable("question3") {
            SurveyScreen(navController, surveyViewModel,3, "Choose model",listOf("Markowitz","Gini"))
        }
        composable("question4") {
            SurveyScreen(navController,surveyViewModel, 4, "Choose your collection",listOf("Indexes(recommended)", "Top indexes", "Indexes and stocks","Top stocks"))
        }
        composable("question5") {
            SurveyScreen(navController, surveyViewModel,5, "For how many years do you want to invest?",listOf("0-2", "2-4", "4-100"))
        }
        composable("question6") {
            SurveyScreen(navController, surveyViewModel,6, "What level of risk do you prefer?",listOf("Low", "Medium", "High"))
        }
        composable("question7") {
            SurveyScreen(navController, surveyViewModel,7, "Which graph do you prefer?",listOf("Safest", "Sharpe", "Max return"))
        }
        composable("summary") {
            SummaryScreen(navController,surveyViewModel)
        }
    }
}

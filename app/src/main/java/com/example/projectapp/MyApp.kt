package com.example.projectapp

import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    NavHost(navController = navController, startDestination = "welcomeScreen") {
        composable("welcomeScreen"){
            WelcomeScreen(navController,authViewModel)
        }
        composable("loginScreen"){
            LoginScreen(navController,authViewModel,sessionManager)
        }
        composable("SignupScreen"){
            SignUpScreen(navController,authViewModel)
        }
        composable("HomeScreen"){
            HomeScreen(navController,surveyViewModel=surveyViewModel)
        }
        composable("ProfileScreen"){
            ProfileScreen(navController,authViewModel,sessionManager)
        }
        composable("changePassword"){
            ChangePasswordScreen(navController)
        }
        composable("question1") {

            SurveyScreen(navController, surveyViewModel,sessionManager,1, "The amount I will deposit into my portfolio in shekels is:",listOf())
        }
        composable("question2") {
            SurveyScreen(navController, surveyViewModel,sessionManager,2, "Do you want to use machine learning?",listOf("No", "Yes"))
        }
        composable("question3") {
            SurveyScreen(navController, surveyViewModel,sessionManager,3, "Choose model",listOf("Markowitz","Gini"))
        }
        composable("question4") {
            SurveyScreen(navController,surveyViewModel, sessionManager,4, "Choose your collection",listOf("Indexes(recommended)", "Top indexes", "Indexes and stocks","Top stocks"))
        }
        composable("question5") {
            SurveyScreen(navController, surveyViewModel,sessionManager,5, "For how many years do you want to invest?",listOf("0-2", "2-4", "4-40"))
        }
        composable("question8") {
            SurveyScreen(navController, surveyViewModel,sessionManager,6, "What level of risk do you prefer?",listOf("Low", "Medium", "High"))
        }
        composable("AboutScreen"){
            AboutScreen(navController)
        }
        composable("ContactUs"){
            ContactUsScreen(navController = navController)
        }
//        composable("question7") {
//            SurveyScreen(navController, surveyViewModel,sessionManager,7, "Which graph do you prefer?",listOf("Safest", "Sharpe", "Max return"))
//        }
        composable("question6"){
            val answer = surveyViewModel.getAnswer(1).takeIf { it.isNotEmpty() } ?: 25000.0
            val risks by surveyViewModel.riskData.collectAsState()
            val lowRisk = risks[0]
            val mediumRisk = risks[1]
            val highRisk = risks[2]
            RiskSelectionDisplay(riskData = listOf(
//                RiskData("Low Risk", 4.5, 6.82, -2.80, -1.18, 0.19, 1.06, 7.98,0.864),
                RiskData("Low Risk", lowRisk.meanYield, lowRisk.stdDev, lowRisk.min, lowRisk.q1, lowRisk.median, lowRisk.q3, lowRisk.max,0.864),
//                RiskData("Medium Risk", 8.67, 17.73, -4.32, -1.84, 0.51, 1.93, 12.00,1.425),
                RiskData("Medium Risk", mediumRisk.meanYield, mediumRisk.stdDev, mediumRisk.min, mediumRisk.q1, mediumRisk.median, mediumRisk.q3, mediumRisk.max,1.425),
//                RiskData("High Risk", 11.34, 25.46, -7.50, -2.50, 0.42, 2.82, 18.67,1.047)
                RiskData("High Risk", highRisk.meanYield, highRisk.stdDev, highRisk.min, highRisk.q1, highRisk.median, highRisk.q3, highRisk.max,1.047)
            ), onRiskSelected = {

                navController.navigate("summary/${answer}")
            },
                surveyViewModel,navController)
        }
        composable("summary/{answer}") {
            SummaryScreen(navController,surveyViewModel)
        }
    }



}


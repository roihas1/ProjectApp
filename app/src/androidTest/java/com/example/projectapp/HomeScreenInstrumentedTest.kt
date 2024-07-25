package com.example.projectapp

import androidx.compose.runtime.remember
import org.junit.Test


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule

import org.junit.runner.RunWith
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule

import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projectapp.viewmodel.SurveyViewModel

@RunWith(AndroidJUnit4::class)
class HomeScreenInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var surveyViewModel: SurveyViewModel
    private  lateinit var sessionManager:SessionManager

    @Before
    fun setUp() {
        composeTestRule.setContent {
            val context = LocalContext.current
            navController = remember {
                TestNavHostController(context).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
            }
            surveyViewModel = SurveyViewModel()
            sessionManager = SessionManager(context)
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(navController = navController, surveyViewModel = surveyViewModel)
                }
                composable("question1") {
                    SurveyScreen(navController, surveyViewModel,sessionManager,1, "The amount I will deposit into my portfolio in shekels is:",listOf())
                }

                composable("ProfileScreen") {
                    // Add a placeholder composable for the profile screen
                }
                composable("AboutScreen") {
                    // Add a placeholder composable for the about screen
                }
            }
        }
    }


@Test
fun testCreateNewPortfolioButtonIsDisplayed() {
    composeTestRule.onNodeWithText("Create A New\n\nInvestment\n\nPortfolio").assertIsDisplayed()
}

@Test
fun testGoToLastPortfolioButtonIsDisplayed() {
    composeTestRule.onNodeWithText("Go To Last\n\n Portfolio").assertIsDisplayed()
}

@Test
fun testCreateNewPortfolioButtonNavigation() {
    composeTestRule.onNodeWithText("Create A New\n\nInvestment\n\nPortfolio").performClick()
    assert(navController.currentDestination?.route == "question1")
}



@Test
fun testBottomNavigationIsDisplayed() {
    composeTestRule.onNodeWithText("Home").assertIsDisplayed()
    composeTestRule.onNodeWithText("Profile").assertIsDisplayed()
    composeTestRule.onNodeWithText("About").assertIsDisplayed()
}

@Test
fun testBottomNavigationHomeIsSelected() {
    composeTestRule.onNodeWithText("Home").assertIsSelected()
}

@Test
fun testBottomNavigationProfileNavigation() {
    composeTestRule.onNodeWithText("Profile").performClick()
    assert(navController.currentDestination?.route == "ProfileScreen")
}

@Test
fun testBottomNavigationAboutNavigation() {
    composeTestRule.onNodeWithText("About").performClick()
    assert(navController.currentDestination?.route == "AboutScreen")
}
}

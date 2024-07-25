package com.example.projectapp
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projectapp.viewmodel.AuthViewModel
import com.example.projectapp.viewmodel.SurveyViewModel
import kotlinx.coroutines.delay

@RunWith(AndroidJUnit4::class)
class LoginScreenInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var viewModel: AuthViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var surveyViewModel: SurveyViewModel

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel = AuthViewModel() // You might need to provide a mock or test implementation
        sessionManager = SessionManager(context) // You might need to provide a mock or test implementation
        surveyViewModel = SurveyViewModel()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(navController = navController, viewModel = viewModel, sessionManager = sessionManager)
                }
                composable("HomeScreen") {
                    HomeScreen(navController = navController, surveyViewModel = surveyViewModel)
                }
            }
        }
    }


    @Test
    fun testLoginTextIsDisplayed() {
        composeTestRule.onNodeWithText("Login to continue").assertIsDisplayed()
    }

    @Test
    fun testUsernameFieldIsDisplayed() {
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
    }

    @Test
    fun testPasswordFieldIsDisplayed() {
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun testLoginButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun testEmptyFieldsShowsToast() {
        composeTestRule.onNodeWithText("Login").performClick()
        // Note: Testing Toast messages in instrumented tests is challenging.
        // Consider using a different approach for error messaging in your app for easier testing.
    }

    @Test
    fun testPasswordToggleVisibility() {
        val passwordField = composeTestRule.onNodeWithText("Password")
        passwordField.performTextInput("rTyuiop0")


        composeTestRule.onNodeWithContentDescription("").performClick()


    }

    @Test
    fun testSuccessfulLogin() {
        // Set up successful login scenario in viewModel
        composeTestRule.onNodeWithText("Username").performTextInput("roi12@post.bgu.ac.il")
        composeTestRule.onNodeWithText("Password").performTextInput("rTyuiop0")

        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            navController.currentDestination?.route == "HomeScreen"
        }


    }

    @Test
     fun testFailedLogin() {
        // Set up failed login scenario in viewModel
        composeTestRule.onNodeWithText("Username").performTextInput("wrong@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("wrongpassword")

        composeTestRule.onNodeWithText("Login").performClick()

        // Check if error message is displayed
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Wrong username or password. Please try again.").fetchSemanticsNodes().isNotEmpty()
        }
    }
}
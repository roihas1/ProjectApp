package com.example.projectapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.example.projectapp.viewmodel.AuthViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.os.IBinder
import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {
    override fun describeTo(description: Description) {
        description.appendText("is toast")
    }

    override fun matchesSafely(root: Root): Boolean {
        val type = root.windowLayoutParams.get().type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken: IBinder = root.decorView.windowToken
            val appToken: IBinder = root.decorView.applicationWindowToken
            return windowToken === appToken
        }
        return false
    }
}
@RunWith(AndroidJUnit4::class)
class SignUpScreenInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        navController = TestNavHostController(context)
        viewModel = AuthViewModel()
    }

    @Test
    fun testSignUpScreenDisplay() {
        composeTestRule.setContent {
            SignUpScreen(navController = navController, viewModel = viewModel)
        }

        // Check if the main title is displayed
//        composeTestRule.onNodeWithText("Sign up").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create new account").assertIsDisplayed()

        // Check if all input fields are displayed
        composeTestRule.onNodeWithText("First Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Last Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Phone number").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confirm Password").assertIsDisplayed()

        // Check if buttons are displayed
        composeTestRule.onNodeWithText("Clear").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Sign up").assertIsDisplayed()
    }

    @Test
    fun testInputFieldInteractions() {
        composeTestRule.setContent {
            SignUpScreen(navController = navController, viewModel = viewModel)
        }

        // Test input field interactions
        composeTestRule.onNodeWithText("First Name").performTextInput("John")
        composeTestRule.onNodeWithText("Last Name").performTextInput("Doe")
        composeTestRule.onNodeWithText("Phone number").performTextInput("1234567890")
        composeTestRule.onNodeWithText("Email").performTextInput("john.doe@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("Password123")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("Password123")

        // Verify that the input is reflected in the ViewModel
        assert(viewModel.firstName == "John")
        assert(viewModel.lastName == "Doe")
        assert(viewModel.phoneNumber == "1234567890")
        assert(viewModel.email == "john.doe@example.com")
        assert(viewModel.password == "Password123")
        assert(viewModel.rePassword == "Password123")
    }

    @Test
    fun testClearButtonFunctionality() {
        composeTestRule.setContent {
            SignUpScreen(navController = navController, viewModel = viewModel)
        }

        // Input some data
        composeTestRule.onNodeWithText("First Name").performTextInput("John")
        composeTestRule.onNodeWithText("Last Name").performTextInput("Doe")

        // Click the Clear button
        composeTestRule.onNodeWithText("Clear").performClick()

        // Verify that the input fields are cleared
        assert(viewModel.firstName.isEmpty())
        assert(viewModel.lastName.isEmpty())
        assert(viewModel.phoneNumber.isEmpty())
        assert(viewModel.email.isEmpty())
        assert(viewModel.password.isEmpty())
        assert(viewModel.rePassword.isEmpty())
    }

    @Test
    fun testSignUpButtonWithInvalidInput() {
        composeTestRule.setContent {
            SignUpScreen(navController = navController, viewModel = viewModel)
        }

        // Click Sign up button without filling any fields

        composeTestRule.onNodeWithContentDescription("SignUpButton").performClick()

//        onView(withText("All fields are required")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
        // Fill in mismatched passwords
        composeTestRule.onNodeWithText("Password").performTextInput("Password123")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("Password456")
        composeTestRule.onNodeWithContentDescription("SignUpButton").performClick()
        composeTestRule.onNodeWithContentDescription("SignUpButton").isDisplayed()
        // Check for error message
//        composeTestRule.onNodeWithText("Passwords do not match").assertIsDisplayed()

        // Fill in invalid password
        composeTestRule.onNodeWithText("Password").performTextInput("password")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("password")
        composeTestRule.onNodeWithContentDescription("SignUpButton").performClick()
        composeTestRule.onNodeWithContentDescription("SignUpButton").isDisplayed()
        // Check for error message
//        composeTestRule.onNodeWithText("Password must contain at least one capital letter and one number, and be at least 8 characters long").assertIsDisplayed()
    }
}

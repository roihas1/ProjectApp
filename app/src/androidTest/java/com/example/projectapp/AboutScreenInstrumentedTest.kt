package com.example.projectapp

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.projectapp.ui.theme.ProjectAppTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AboutScreenInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun aboutScreen_displaysCorrectTexts() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            ProjectAppTheme {
                AboutScreen(navController = navController)
            }
        }

        // Check if texts are displayed
        composeTestRule.onNodeWithText("About the App").assertExists()
        composeTestRule.onNodeWithText("Welcome to our recommendation portfolio app. " +
                "Our goal is to provide you the best portfolio for you!").assertExists()
        composeTestRule.onNodeWithText("What is a Portfolio?").assertExists()
        composeTestRule.onNodeWithText("Why is it Important to Invest?").assertExists()
    }

    @Test
    fun aboutScreen_hasContactUsButton() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            ProjectAppTheme {
                AboutScreen(navController = navController)
            }
        }

        // Check if Contact Us button exists and is clickable
        composeTestRule.onNodeWithText("Contact Us").assertExists().assertHasClickAction()
    }

    @Test
    fun teamSection_displaysTeamMembers() {
        composeTestRule.setContent {
            ProjectAppTheme {
                TeamSection()
            }
        }

        // Check if Team section title exists
        composeTestRule.onNodeWithText("Team").assertExists()

        // Check if each team member's name exists
        val teamMembers = listOf("Roy Turiski", "Ofer Waldmann", "Erez Kimmel", "Roi Hass")
        teamMembers.forEach { name ->
            composeTestRule.onNodeWithText(name).assertExists()
        }
    }

    @Test
    fun teamMemberCard_isClickable() {
        composeTestRule.setContent {
            ProjectAppTheme {
                TeamMemberCard(name = "Roy Turiski", linkedInUrl = "https://www.linkedin.com/in/roy-turiski/")
            }
        }

        // Check if the card is clickable
        composeTestRule.onNodeWithText("Roy Turiski").assertExists().assertHasClickAction()
    }

    @Test
    fun teamGrid_displaysAllMembers() {
        val members = listOf(
            "Roy Turiski" to "https://www.linkedin.com/in/roy-turiski/",
            "Ofer Waldmann" to "https://www.linkedin.com/in/ofer-waldmann-2325b6176/",
            "Erez Kimmel" to "https://www.linkedin.com/in/erez-kimmel/",
            "Roi Hass" to "https://www.linkedin.com/in/roi-hass/"
        )

        composeTestRule.setContent {
            ProjectAppTheme {
                TeamGrid(members)
            }
        }

        // Check if each team member's name exists
        members.forEach { (name, _) ->
            composeTestRule.onNodeWithText(name).assertExists()
        }
    }


}
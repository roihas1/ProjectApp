
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
//import androidx.compose.ui.test.assertExists
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.projectapp.AboutScreen
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [29])
class AboutScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        // Set necessary system properties for Robolectric
        System.setProperty("ro.build.fingerprint", "generic/test-keys")
        System.setProperty("ro.product.manufacturer", "generic")
        System.setProperty("ro.build.version.sdk", "30")
    }
    @Test
    fun aboutScreen_displaysCorrectTexts() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            AboutScreen(navController = navController)
        }

        // Check if texts are displayed
        composeTestRule.onNodeWithText("About the App").assertExists()
//        composeTestRule.onNodeWithText("Welcome to our recommendation portfolio app.").assertExists()
//        composeTestRule.onNodeWithText("We are Software and Information Systems engineers with expertise in Machine Learning and a passion for investments.").assertExists()
//        composeTestRule.onNodeWithText("What is a Portfolio?").assertExists()
//        composeTestRule.onNodeWithText("Why is it Important to Invest?").assertExists()
    }

//    @Test
//    fun aboutScreen_contactUsButtonNavigates() {
//        composeTestRule.setContent {
//            val navController = rememberNavController()
//            AboutScreen(navController = navController)
//        }
//
//        // Perform click on "Contact Us" button
//        composeTestRule.onNodeWithText("Contact Us").performClick()
//
//        // Verify the navigation
//        // Note: You may need to set up your NavController to assert the navigation
//        // For simplicity, we are just checking if the button is clickable
//    }

//    @Test
//    fun aboutScreen_teamMembersDisplayed() {
//        composeTestRule.setContent {
//            val navController = rememberNavController()
//            AboutScreen(navController = navController)
//        }
//
//        // Check if team members are displayed
//        composeTestRule.onNodeWithText("Roy Turiski").assertExists()
//        composeTestRule.onNodeWithText("Ofer Waldmann").assertExists()
//        composeTestRule.onNodeWithText("Erez Kimmel").assertExists()
//        composeTestRule.onNodeWithText("Roi Hass").assertExists()
//    }
}

//class AboutScreenNavigationTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun aboutScreen_contactUsButtonNavigates() {
//        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
//
//        composeTestRule.setContent {
//            navController.setGraph(R.navigation.nav_graph)
//            AboutScreen(navController = navController)
//        }
//
//        // Perform click on "Contact Us" button
//        composeTestRule.onNodeWithText("Contact Us").performClick()
//
//        // Verify the navigation
//        assert(navController.currentDestination?.route == "ContactUs")
//    }
//}

package com.example.projectapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.projectapp.viewmodel.SurveyViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RiskSelectionInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var surveyViewModel: SurveyViewModel

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        navController = TestNavHostController(context)
        surveyViewModel = SurveyViewModel()
    }

    @Test
    fun testRiskSelectionDisplay() {
        val riskData = listOf(
            RiskData("Low Risk", 5.0, 3.0, 2.0, 3.0, 5.0, 7.0, 8.0, 1.5),
            RiskData("Medium Risk", 8.0, 5.0, 3.0, 5.0, 8.0, 11.0, 13.0, 1.8),
            RiskData("High Risk", 12.0, 8.0, 4.0, 8.0, 12.0, 16.0, 20.0, 2.0)
        )

        composeTestRule.setContent {
            RiskSelectionDisplay(
                riskData = riskData,
                onRiskSelected = {},
                surveyViewModel = surveyViewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText("Select Your Risk Level").assertIsDisplayed()
        composeTestRule.onNodeWithText("Low Risk").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confirm Low Risk Selection").assertIsDisplayed()
    }

    @Test
    fun testKeyStatisticsCard() {
        val riskData = RiskData("Medium Risk", 8.0, 5.0, 3.0, 5.0, 8.0, 11.0, 13.0, 1.8)

        composeTestRule.setContent {
            KeyStatisticsCard(riskData)
        }

        composeTestRule.onNodeWithText("Annual Return").assertIsDisplayed()
        composeTestRule.onNodeWithText("8.00%").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sharpe").assertIsDisplayed()
        composeTestRule.onNodeWithText("1.80").assertIsDisplayed()
        composeTestRule.onNodeWithText("Volatility").assertIsDisplayed()
        composeTestRule.onNodeWithText("5.00%").assertIsDisplayed()
    }

    @Test
    fun testRiskDescriptionCard() {
        val riskData = RiskData("Medium Risk", 8.0, 5.0, 3.0, 5.0, 8.0, 11.0, 13.0, 1.8)

        composeTestRule.setContent {
            RiskDescriptionCard(riskData)
        }

        composeTestRule.onNodeWithText("Risk Level: Medium Risk").assertIsDisplayed()
        composeTestRule.onNodeWithText("Balanced approach with moderate potential returns and volatility.").assertIsDisplayed()
    }

    @Test
    fun testReturnRangeCard() {
        val riskData = RiskData("Medium Risk", 8.0, 5.0, 3.0, 5.0, 8.0, 11.0, 13.0, 1.8)

        composeTestRule.setContent {
            ReturnRangeCard(surveyViewModel, riskData)
        }

        composeTestRule.onNodeWithText("Investment Amount: 25,000.00").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Projected Returns for medium risk over 4 Years:").assertIsDisplayed()
        // Note: The exact range values might vary, so we're not asserting on those
    }

    @Test
    fun testRiskLevelSelector() {
        composeTestRule.setContent {
            RiskLevelSelector(
                currentRiskLevel = "Medium Risk",
                onPrevious = {},
                onNext = {}
            )
        }

        composeTestRule.onNodeWithText("Medium Risk").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Previous").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Next").assertIsDisplayed()
    }

    @Test
    fun testInfoDialog() {
        composeTestRule.setContent {
            InfoDialog(
                showInfoDialog = true,
                label = "Volatility",
                onDismissRequest = {}
            )
        }

        composeTestRule.onNodeWithText("What is Volatility?").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Volatility measures the risk of your investment, indicating how much the value of the asset fluctuates over time.").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    }
}
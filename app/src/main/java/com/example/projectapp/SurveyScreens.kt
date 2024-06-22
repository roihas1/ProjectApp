package com.example.projectapp
import android.icu.text.NumberFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.projectapp.ui.theme.ProjectAppTheme
import com.example.projectapp.viewmodel.SurveyViewModel
import kotlinx.coroutines.launch


@Composable
fun SurveyScreen(navController: NavHostController, viewModel: SurveyViewModel, questionNumber: Int, question: String, answers: List<String>) {
    var selectedAnswer by remember { mutableStateOf(viewModel.getAnswer(questionNumber)) }
    var showDialogForAnswer by remember { mutableStateOf(false) }
    var answerClicked by remember { mutableStateOf("") }
    var investmentAmount by remember { mutableStateOf(405610) }
    var textInput by remember { mutableStateOf(investmentAmount.toString()) }
    var firstResponse by remember { mutableStateOf(listOf(listOf(""))) }
    val coroutineScope = rememberCoroutineScope()

    val allStocks = listOf(
        listOf(
            "601 - All-Bond כללי",
            "602 - תל גוב-כללי",
            "700 - תל גוב-שקלי",
            "701 - תל גוב-משתנה",
            "702 - תל גוב-שקלי 0-2",
            "142 - ת\"א-35",
            "143 - ת\"א-90",
            "SPY - SPDR S&P 500",
            "QQQ - Invesco QQQ Trust, Series 1",
            "^RUT - Russell 2000",
            "IEI - iShares 3-7 Year Treasury Bond",
            "LQD - iShares iBoxx $ Investment Grade",
            "GSG - iShares GSCI Commodity-Indexed",
            "GLD - SPDR Gold Trust",
            "OIL - iPath Pure Beta Crude Oil ETN"
        ),
        listOf(
            "658 - תל גוב-צמודות 5-10",
            "690 - תל גוב-לא צמודות",
            "637 - תל גוב- צמודות 0-2",
            "XLK - SPDR Select Sector Fund - Techn",
            "SHV - iShares Short Treasury Bond ETF",
            "700 - תל גוב-שקלי",
            "704 - תל גוב-שקלי 5+",
            "QQQ - Invesco QQQ Trust, Series 1",
            "IEI - iShares 3-7 Year Treasury Bond",
            "LQD - iShares iBoxx $ Investment Grade",
            "GLD - SPDR Gold Trust",
            "127 - חיפושי גז ונפט מניות והמירים",
            "143 - ת\"א-90",
            "603 - אג\"ח כללי - קונצרני",
            "702 - תל גוב-שקלי 0-2",
            "712 - תל בונד צמודות-יתר",
            "170 - ת\"א-נפט וגז",
            "OIL - iPath Pure Beta Crude Oil ETN",
            "701 - תל גוב-משתנה"
        ),
        listOf(
            "170 - ת\"א-נפט וגז",
            "603 - אג\"ח כללי - קונצרני",
            "601 - All-Bond כללי",
            "690 - תל גוב-לא צמודות",
            "637 - תל גוב-צמודות 0-2",
            "704 - תל גוב-שקלי 5+",
            "701 - תל גוב-משתנה",
            "714 - תל בונד-תשואות",
            "XLK - SPDR Select Sector Fund - Techn",
            "XLV - SPDR Select Sector Fund - Healt",
            "XOP - SPDR S&P Oil & Gas Explor & Pro",
            "QQQ - Invesco QQQ Trust, Series 1",
            "SHV - iShares Short Treasury Bond ETF",
            "273011 - נייס",
            "695437 - מזרחי טפחות",
            "593038 - בינלאומי",
            "LMB - Limbach Holdings Inc. Common Stock",
            "AGM - Federal Agricultural Mortgage Corporation Common Stock",
            "TDS - Telephone and Data Systems Inc. Common Shares",
            "MCD - McDonald's Corporation Common Stock",
            "FMY - First Trust Motgage Income Fund Common Shares of Beneficial Interest",
            "EOI - Eaton Vance Enhance Equity Income Fund Eaton Vance Enhanced Equity Income Fund Shares of Beneficial Interest"
        ),
        listOf(
            "TSLA - Tesla Inc. Common Stock",
            "ADBE - Adobe Inc. Common Stock",
            "JNJ - Johnson & Johnson Common Stock",
            "EOI - Eaton Vance Enhance Equity Income Fund Eaton Vance Enhanced Equity Income Fund Shares of Beneficial Interest",
            "AAPL - Apple Inc. Common Stock",
            "AMZN - Amazon.com Inc. Common Stock",
            "MSFT - Microsoft Corporation Common Stock",
            "GOOG - Alphabet Inc. Class C Capital Stock",
            "META - Meta Platforms Inc. Class A Common Stock",
            "NVDA - NVIDIA Corporation Common Stock",
            "AJG - Arthur J. Gallagher & Co. Common Stock",
            "BRO - Brown & Brown Inc. Common Stock",
            "BAC - Bank of America Corporation Common Stock",
            "CSL - Carlisle Companies Incorporated Common Stock",
            "593038 - בינלאומי",
            "273011 - נייס",
            "442012 - סיאי",
            "1081124 - אלביט מערכות"
        )
    )
    var clickedStocks by remember {mutableIntStateOf(-1) }
    var clickedRisks by remember {mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Text(
            modifier = Modifier
                .padding(24.dp),
            text = question,
            color = Color.White,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            answers.forEach { answer ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedAnswer == answer,
                        onClick = {
                            selectedAnswer = answer
                            viewModel.saveAnswer(questionNumber, answer)
                                  },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.White.copy(alpha = 0.6f)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (questionNumber == 4){
                        TextButton(onClick = {
                            showDialogForAnswer = true
                            if (answer == "Indexes(recommended)") {
                                clickedStocks = 0
                                answerClicked = "Indexes(recommended)"
                            }
                            else if (answer == "Top indexes"){
                                clickedStocks = 1
                                answerClicked = "Top indexes"
                            }
                            else if (answer == "Indexes and stocks"){
                                clickedStocks = 2
                                answerClicked = "Indexes and stocks"
                            }
                            else if (answer == "Top stocks"){
                                clickedStocks = 3
                                answerClicked = "Top stocks"
                            }
                        }) {
                            Text(
                                text = answer,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                            )

                        }
                    }
                    else if (questionNumber == 6) {
                        showDialogForAnswer = true
                        when(answer){
                            "Low" -> {
                                clickedRisks = 0
                                answerClicked ="Low"
                            }
                            "Medium" -> {
                                clickedRisks = 1
                                answerClicked ="Medium"
                            }
                            "High" -> {
                                clickedRisks = 2
                                answerClicked ="High"
                            }
                        }
                    }
                    else {
                        Text(
                            text = answer,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
        if (questionNumber == 1) {

            CustomTextField(
                value = viewModel.getAnswer(1),
                label = "Amount",
                onValueChange = { newValue ->
                    textInput = newValue
                    val newAmount = newValue.toIntOrNull()
                    if (newAmount != null) {
                        investmentAmount = newAmount
                        viewModel.saveAnswer(questionNumber, textInput)
                    }
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "₪ 25,000")
                var sliderAmount = if (viewModel.getAnswer(1) == "")  investmentAmount else viewModel.getAnswer(1).toFloat()
                Slider(
                    value = sliderAmount.toFloat() ,
                    onValueChange = { newValue ->
                        investmentAmount = newValue.toInt()
                        textInput = newValue.toInt().toString()
                        viewModel.saveAnswer(questionNumber, textInput)
                    },
                    valueRange = 25000f..1000000f,
                    steps = 0,
                    modifier = Modifier.weight(1f)
                )
                Text(text = "₪ 1,000,000")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FunctionButton(
                modifier = Modifier,
                onClick = { if (questionNumber > 1) navController.navigate("question${questionNumber - 1}") },
                text = "Back",
                buttonWidth = 130.dp,
                enabled = questionNumber > 1  // Disable the button for the first question
            )

            if (questionNumber < 7) {
                FunctionButton(
                    modifier = Modifier,
                    onClick = {
                        if (questionNumber == 5){
                            coroutineScope.launch {
                                firstResponse = viewModel.firstForm(viewModel.getAnswers().value) as List<List<String>>

                            }
                        }
                            navController.navigate("question${questionNumber + 1}") },
                    text = "Next",
                    buttonWidth = 120.dp
                )
            } else {
                FunctionButton(
                    modifier = Modifier,
                    onClick = {  if (viewModel.isSurveyComplete()) navController.navigate("summary") },
                    text = "Finish",
                    buttonWidth = 130.dp,
                    enabled = viewModel.isSurveyComplete()
                )

            }
        }
            if (showDialogForAnswer) {
                if (questionNumber == 6){
                    StockListDialog(
                        onDismissRequest = { showDialogForAnswer = false },
                        stockList = firstResponse[clickedRisks],
                        portfolioDescription = answerClicked
                    )
                }
                StockListDialog(
                    onDismissRequest = { showDialogForAnswer = false },
                    stockList = allStocks[clickedStocks],
                    portfolioDescription = answerClicked
                )
            }
        BottomNavigation(navController)
    }
}
private fun formatCurrency(amount: String): String {
    val format = NumberFormat.getCurrencyInstance(java.util.Locale("he", "IL"))
    format.maximumFractionDigits = 0
    return format.format(amount)
}
@Composable
fun StockListDialog(
    onDismissRequest: () -> Unit,
    stockList: List<String>,
    portfolioDescription: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = portfolioDescription, style = MaterialTheme.typography.bodyMedium)
        },
        text = {
            LazyColumn {
                items(stockList) { stock ->
                    Text(
                        text = stock,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                // Handle the selection of the stock/index
                                onDismissRequest()
                            }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("Close")
            }
        }
    )
}


@Composable
fun SummaryScreen(navController: NavHostController,viewModel: SurveyViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = "Thank you for completing the survey!",
            color= Color.White,
            style = MaterialTheme.typography.labelMedium)
        FunctionButton(
            modifier = Modifier,
            onClick = {
                viewModel.clearAnswers()
                navController.navigate("HomeScreen")
                      },
            text = "Back Home Page",
            buttonWidth = 260.dp
        )
        BottomNavigation(navController)
    }
}
@Composable
@Preview
fun StockListDialogPreview(){
    ProjectAppTheme{
        val stockList = listOf(
        "AAPL", "GOOGL", "MSFT", "AMZN", "TSLA",
        "FB", "NASDAQ", "S&P 500", "DOW JONES"
        )
        StockListDialog( onDismissRequest = { var showDialogForAnswer = null },
            stockList = stockList,
            portfolioDescription = "Portfolio number 1")
    }
}
@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        val viewModel = SurveyViewModel()
        SurveyScreen(navController,viewModel,1,"Thank you for completing the survey!",listOf("Apple", "Banana", "Cherry"))
    }
    
}
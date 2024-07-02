package com.example.projectapp
import android.icu.text.NumberFormat
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projectapp.ui.theme.ProjectAppTheme
import com.example.projectapp.viewmodel.SurveyState
import com.example.projectapp.viewmodel.SurveyViewModel
import kotlinx.coroutines.launch


@Composable
fun SurveyScreen(navController: NavHostController, viewModel: SurveyViewModel,sessionManager:SessionManager, questionNumber: Int, question: String, answers: List<String>) {
    var selectedAnswer by remember { mutableStateOf(viewModel.getAnswer(questionNumber)) }
    var showDialogForAnswer by remember { mutableStateOf(false) }
    var answerClicked by remember { mutableStateOf("") }
    var investmentAmount by remember { mutableStateOf(405610) }
    var textInput by remember { mutableStateOf(investmentAmount.toString()) }
    var firstResponse by remember { mutableStateOf(listOf(listOf(""))) }
    val coroutineScope = rememberCoroutineScope()
    var surveyState = viewModel.surveyState
    val context = LocalContext.current
    firstResponse = listOf(
        listOf(
            "Low Risk",
            "Mean Yield :0.4149368649331199",
            "Standard Deviation: 6.8234870555395695",
            "Min: -14.803441054031197",
            "25%(Q1): -1.1837818990004996",
            "50%(Median): 0.18611909742770605",
            "75%(Q3): 1.0618668404211329",
            "Max: 18.981342001355127"
        ),
        listOf(
            "Medium Risk: ",
            "Mean Yield: 1.9079042224865659",
            "Standard Deviation: 17.72849496636662",
            "Min: -30.31707048201071",
            "25%(Q1): -1.8383755575164251",
            "50%(Median): 0.5120410824436372",
            "75%(Q3): 1.9341370638133903",
            "Max: 46.00383736978544"
        ),
        listOf(
            "High Risk: ",
            "Mean Yield: 3.509537738115477",
            "Standard Deviation: 25.456954900845325",
            "Min: -37.501358309568325",
            "25%(Q1): -2.4950099225715867",
            "50%(Median): 0.41986761394164906",
            "75%(Q3): 2.818111354423769",
            "Max: 67.66943322526102"
        )
    )

    val allStocks = listOf(
        listOf(
            "This portfolio focuses on diversified investments across major market indices," +
                    " offering exposure to a wide range of sectors and companies. " +
                    "Ideal for investors seeking broad market performance and stability through established index funds."
        ),
        listOf(
            "Featuring high-performing indices from global markets," +
                    " this portfolio targets indices known for strong historical returns and stability." +
                    " Investors benefit from exposure to leading economies and sectors driving market growth."
        ),
        listOf(
            "Combining selected indices with individual stock picks, " +
                    "this portfolio offers a balanced approach between broad market exposure and potential high-growth opportunities." +
                    " Investors can diversify across sectors while capitalizing on specific company performance."
        ),
        listOf(
            "Designed for investors focused on individual companies with exceptional growth potential," +
                    " this portfolio includes carefully chosen stocks known for strong fundamentals and market leadership." +
                    " It aims to outperform broader indices through strategic stock selection and research-backed investments."
        )
//        listOf(
//            "601 - All-Bond כללי",
//            "602 - תל גוב-כללי",
//            "700 - תל גוב-שקלי",
//            "701 - תל גוב-משתנה",
//            "702 - תל גוב-שקלי 0-2",
//            "142 - ת\"א-35",
//            "143 - ת\"א-90",
//            "SPY - SPDR S&P 500",
//            "QQQ - Invesco QQQ Trust, Series 1",
//            "^RUT - Russell 2000",
//            "IEI - iShares 3-7 Year Treasury Bond",
//            "LQD - iShares iBoxx $ Investment Grade",
//            "GSG - iShares GSCI Commodity-Indexed",
//            "GLD - SPDR Gold Trust",
//            "OIL - iPath Pure Beta Crude Oil ETN"
//        ),
//        listOf(
//            "658 - תל גוב-צמודות 5-10",
//            "690 - תל גוב-לא צמודות",
//            "637 - תל גוב- צמודות 0-2",
//            "XLK - SPDR Select Sector Fund - Techn",
//            "SHV - iShares Short Treasury Bond ETF",
//            "700 - תל גוב-שקלי",
//            "704 - תל גוב-שקלי 5+",
//            "QQQ - Invesco QQQ Trust, Series 1",
//            "IEI - iShares 3-7 Year Treasury Bond",
//            "LQD - iShares iBoxx $ Investment Grade",
//            "GLD - SPDR Gold Trust",
//            "127 - חיפושי גז ונפט מניות והמירים",
//            "143 - ת\"א-90",
//            "603 - אג\"ח כללי - קונצרני",
//            "702 - תל גוב-שקלי 0-2",
//            "712 - תל בונד צמודות-יתר",
//            "170 - ת\"א-נפט וגז",
//            "OIL - iPath Pure Beta Crude Oil ETN",
//            "701 - תל גוב-משתנה"
//        ),
//        listOf(
//            "170 - ת\"א-נפט וגז",
//            "603 - אג\"ח כללי - קונצרני",
//            "601 - All-Bond כללי",
//            "690 - תל גוב-לא צמודות",
//            "637 - תל גוב-צמודות 0-2",
//            "704 - תל גוב-שקלי 5+",
//            "701 - תל גוב-משתנה",
//            "714 - תל בונד-תשואות",
//            "XLK - SPDR Select Sector Fund - Techn",
//            "XLV - SPDR Select Sector Fund - Healt",
//            "XOP - SPDR S&P Oil & Gas Explor & Pro",
//            "QQQ - Invesco QQQ Trust, Series 1",
//            "SHV - iShares Short Treasury Bond ETF",
//            "273011 - נייס",
//            "695437 - מזרחי טפחות",
//            "593038 - בינלאומי",
//            "LMB - Limbach Holdings Inc. Common Stock",
//            "AGM - Federal Agricultural Mortgage Corporation Common Stock",
//            "TDS - Telephone and Data Systems Inc. Common Shares",
//            "MCD - McDonald's Corporation Common Stock",
//            "FMY - First Trust Motgage Income Fund Common Shares of Beneficial Interest",
//            "EOI - Eaton Vance Enhance Equity Income Fund Eaton Vance Enhanced Equity Income Fund Shares of Beneficial Interest"
//        ),
//        listOf(
//            "TSLA - Tesla Inc. Common Stock",
//            "ADBE - Adobe Inc. Common Stock",
//            "JNJ - Johnson & Johnson Common Stock",
//            "EOI - Eaton Vance Enhance Equity Income Fund Eaton Vance Enhanced Equity Income Fund Shares of Beneficial Interest",
//            "AAPL - Apple Inc. Common Stock",
//            "AMZN - Amazon.com Inc. Common Stock",
//            "MSFT - Microsoft Corporation Common Stock",
//            "GOOG - Alphabet Inc. Class C Capital Stock",
//            "META - Meta Platforms Inc. Class A Common Stock",
//            "NVDA - NVIDIA Corporation Common Stock",
//            "AJG - Arthur J. Gallagher & Co. Common Stock",
//            "BRO - Brown & Brown Inc. Common Stock",
//            "BAC - Bank of America Corporation Common Stock",
//            "CSL - Carlisle Companies Incorporated Common Stock",
//            "593038 - בינלאומי",
//            "273011 - נייס",
//            "442012 - סיאי",
//            "1081124 - אלביט מערכות"
//        )
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
                        TextButton(
                            onClick = {
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
                            } }) {
                            Text(
                                text = answer,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                            )

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
                val sliderAmount = if (viewModel.getAnswer(1) == "")  investmentAmount else viewModel.getAnswer(1).toFloat()
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
                                firstResponse = viewModel.firstForm(viewModel.getAnswers().value, sessionManager = sessionManager) as List<List<String>>
                            }
                        }
                        if (questionNumber ==5){
                            navController.navigate("question6")
                        }else{
                            navController.navigate("question${questionNumber + 1}")
                             }
                              },

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
                }else {
                    StockListDialog(
                        onDismissRequest = { showDialogForAnswer = false },
                        stockList = allStocks[clickedStocks],
                        portfolioDescription = answerClicked
                    )
                }
            }
        BottomNavigation(navController)
    }

    when (surveyState) {
        is SurveyState.Loading -> {

        }
        is SurveyState.Success -> {
            Text("Successful!")
        }
        is SurveyState.Error -> {
            Toast.makeText(context, surveyState.message, Toast.LENGTH_LONG)
                .show()
            Log.e("fff",surveyState.message)

        }
        else -> {}
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
    val labels = listOf(
        "601 Weight",
        "602 Weight",
        "700 Weight",
        "701 Weight",
        "702 Weight",
        "142 Weight",
        "143 Weight",
        "SPY Weight",
        "QQQ Weight",
        "^RUT Weight",
        "IEI Weight",
        "LQD Weight"
    )
    val weights = listOf(
        0.1984728351097274,
        0.0596214738325715,
        0.0111922971923062,
        0.3505506045618785,
        0.2628909022713126,
        0.0088979980053999,
        0.0310551766001429,
        0.0101047076399928,
        0.0275666378367544,
        0.0018452407977424,
        0.029759780932897,
        0.0080423452192741
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = "Welcome to Your Portfolio Summary",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Below, you can see the breakdown of your portfolio",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
        }
        Divider(modifier = Modifier.padding(horizontal = 8.dp), color = Color.White, thickness = 1.dp)
        Text(
            text = "My investment: 25,000",
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )

        PieChartScreen(
            labels = labels,
            fractions =weights,
            investAmount = 25000.0
        )

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
//@Composable
//@Preview(showBackground = true)
//fun DefaultPreview() {
//    ProjectAppTheme {
//        val navController = rememberNavController()
//        val viewModel = SurveyViewModel()
//        SurveyScreen(navController,viewModel,1,"Thank you for completing the survey!",listOf("Apple", "Banana", "Cherry"))
//    }
//
//}
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.projectapp.ui.theme.ProjectAppTheme
import com.example.projectapp.viewmodel.SurveyState
import com.example.projectapp.viewmodel.SurveyViewModel
import kotlinx.coroutines.launch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay


@Composable
fun InformationSection(
    questionNumber: Int,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing), label = ""
    )

    Column(modifier = modifier.padding(16.dp)) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = MyColors.Primary)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Why is it important?",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = if (expanded) "Collapse" else "Expand",
                            tint = Color.White,
                            modifier = Modifier.rotate(rotationState)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically(
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                    exit = shrinkVertically(
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))
                ) {
                    val infoText = when (questionNumber) {
                        1 -> "This minimum amount is intended to allow us to build an investment portfolio, which will be sufficiently spread over a variety of avenues, according to the needs of each client."
                        2 -> "Asking whether you want to use machine learning allows us to utilize advanced analytics for making informed decisions and creating personalized strategies."
                        3 -> "Choosing between the Markowitz or Gini model lets us align the investment approach with your comfort level and understanding of risk. The Markowitz model focuses on optimizing returns based on risk, while the Gini model emphasizes income distribution and inequality measures."
                        4 -> "Selecting your preferred collection, whether indexes or stocks, determines the focus of your investments, helping us decide between a broad market exposure or a high-reward strategy.\nClick on the answer to learn more."
                        5 -> "Knowing your investment horizon ensures that we match assets and strategies with your time frame, aligning them with your long-term financial goals."
                        else -> ""
                    }

                    Text(
                        text = infoText,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
}

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
    var expanded by remember { mutableStateOf(false) }

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
                .padding(20.dp),
            text = question,
            color = Color.White,
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        InformationSection(questionNumber)

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
                Text(
                    text = "₪ 25,000",
                    color=Color.White
                )
                val sliderAmount = if (viewModel.getAnswer(1) == "")  investmentAmount else viewModel.getAnswer(1).toFloat()
                Slider(
                    value = sliderAmount.toFloat() ,
                    onValueChange = { newValue ->
                        investmentAmount = newValue.toInt()
                        textInput = newValue.toInt().toString()
                        viewModel.saveAnswer(questionNumber, textInput)
                    },
                    valueRange = 25000f..500000f,
                    steps = 0,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "₪ 500,000",
                    color=Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FunctionButton(
                modifier = Modifier,
                onClick = { if (questionNumber > 1) navController.popBackStack()},
                text = "Back",
                buttonWidth = 130.dp,
                enabled = questionNumber > 1  // Disable the button for the first question
            )

            if (questionNumber < 6) {
                FunctionButton(
                    modifier = Modifier,
                    onClick = {
                        if (questionNumber == 5){
                            coroutineScope.launch {
//                                firstResponse = viewModel.firstForm(viewModel.getAnswers().value, sessionManager = sessionManager) as List<List<String>>

                            }
                        }
                        if (questionNumber == 5){
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

@Composable
fun StockListDialog(
    onDismissRequest: () -> Unit,
    stockList: List<String>,
    portfolioDescription: String
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + scaleIn(initialScale = 0.8f) + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + scaleOut(targetScale = 0.8f) + slideOutVertically(targetOffsetY = { it })
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth(0.8f)  // Adjusted width
                    .fillMaxHeight(0.5f) // Adjusted height
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = portfolioDescription,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        itemsIndexed(stockList) { index, stock ->
                            var itemVisible by remember { mutableStateOf(false) }
                            LaunchedEffect(Unit) {
                                delay(index * 50L)
                                itemVisible = true
                            }
                            AnimatedVisibility(
                                visible = itemVisible,
                                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
                            ) {
                                Text(
                                    text = stock,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            // Handle the selection of the stock/index
                                            onDismissRequest()
                                        }
                                        .padding(vertical = 12.dp, horizontal = 8.dp)
                                )
                            }
                            if (index < stockList.lastIndex) {
                                Divider()
                            }
                        }
                    }

                    TextButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}


@Composable
fun SummaryScreen(navController: NavHostController,viewModel: SurveyViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val answer1 = navBackStackEntry?.arguments?.getString("answer")
    val labels = listOf(
        "TSLA - Tesla Inc.",
            "ADBE - Adobe Inc.",
            "JNJ - Johnson & Johnson",
            "EOI - Eaton Vance Enhance Equity Income Fund",
            "AAPL - Apple Inc.",
            "AMZN - Amazon.com Inc.",
            "MSFT - Microsoft Corporation",
            "GOOG - Alphabet Inc.",
            "META - Meta Platforms Inc.",
            "NVDA - NVIDIA Corporation",
            "AJG - Arthur J. Gallagher & Co.",
            "BRO - Brown & Brown Inc.",
            "BAC - Bank of America Corporation",
            "CSL - Carlisle Companies Incorporated",
            "593038 - בינלאומי",
            "273011 - נייס",
            "442012 - סיאי",
            "1081124 - אלביט מערכות"
//        )

    )
    val weights = listOf(
        0.10,  // TSLA - Tesla Inc. Common Stock
        0.06,  // ADBE - Adobe Inc. Common Stock
        0.07,  // JNJ - Johnson & Johnson Common Stock
        0.03,  // EOI - Eaton Vance Enhance Equity Income Fund
        0.18,  // AAPL - Apple Inc. Common Stock
        0.12,  // AMZN - Amazon.com Inc. Common Stock
        0.15,  // MSFT - Microsoft Corporation Common Stock
        0.06,  // GOOG - Alphabet Inc. Class C Capital Stock
        0.04,  // META - Meta Platforms Inc. Class A Common Stock
        0.05,  // NVDA - NVIDIA Corporation Common Stock
        0.02,  // AJG - Arthur J. Gallagher & Co. Common Stock
        0.02,  // BRO - Brown & Brown Inc. Common Stock
        0.04,  // BAC - Bank of America Corporation Common Stock
        0.02,  // CSL - Carlisle Companies Incorporated Common Stock
        0.01,  // 593038 - בינלאומי
        0.01,  // 273011 - נייס
        0.01,  // 442012 - סיאי
        0.01   // 1081124 - אלביט מערכות
    )
    val clearAnswersAndNavigate = {
        viewModel.clearAnswers()
        navController.navigate("HomeScreen")
    }

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
                text = "Your Portfolio Summary",
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
            text = "My investment: $answer1",
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text="Tap on the Chart",
            color = Color.White,
            fontSize = 10.sp
        )

        if (answer1 != null) {
            PieChartScreen(
                labels = labels,
                fractions = weights,
                investAmount = answer1.toDouble()
            )
        }
        Row {
            FunctionButton(
                modifier = Modifier,
                onClick = clearAnswersAndNavigate,
                text = "Back",
                buttonWidth = 260.dp,
                textSize = 20.sp
            )
            FunctionButton(
                onClick = { /*TODO*/ },
                text = "Save to ",
                buttonWidth = 160.dp,
                withIcon = true,
                textSize = 20.sp
            )

        }
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
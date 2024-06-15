package com.example.projectapp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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


@Composable
fun SurveyScreen(navController: NavHostController, viewModel: SurveyViewModel, questionNumber: Int, question: String, answers: List<String>) {
    var selectedAnswer by remember { mutableStateOf("") }
    var showDialogForAnswer by remember { mutableStateOf(false) }
    var answerClicked by remember { mutableStateOf("") }
    val allStocks = listOf(
        listOf(
            "AAPL", "MSFT", "GOOGL", "AMZN", "FB",
            "TSLA", "NVDA", "INTC", "ADBE", "CSCO"
        ),
        listOf(
            "PG", "KO", "PEP", "PM", "MO",
            "KMB", "CL", "HSY", "K", "GIS"
        ),
        listOf(
            "JPM", "BAC", "WFC", "C", "GS",
            "MS", "AXP", "BK", "USB", "SCHW"
        ),
        listOf(
            "SPX", "IXIC", "DJI", "RUT", "FTSE",
            "DAX", "CAC", "NIKKEI", "HSI", "ASX"
        )
    )
    var clickedStocks by remember {mutableIntStateOf(-1) }


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
                    if (questionNumber == 3){
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

            if (questionNumber < 6) {
                FunctionButton(
                    modifier = Modifier,
                    onClick = { navController.navigate("question${questionNumber + 1}") },
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
                StockListDialog(
                    onDismissRequest = { showDialogForAnswer = false },
                    stockList = allStocks[clickedStocks],
                    portfolioDescription = answerClicked
                )
            }


        BottomNavigation(navController)
    }
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
fun SummaryScreen(navController: NavHostController) {

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
            onClick = { navController.navigate("HomeScreen") },
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
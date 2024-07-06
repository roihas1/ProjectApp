package com.example.projectapp
import java.text.NumberFormat
import java.util.Locale
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projectapp.viewmodel.SurveyViewModel
import kotlin.time.times
import kotlin.math.pow

data class RiskData(
    val level: String,
    val meanYield: Double,
    val stdDev: Double,
    val min: Double,
    val q1: Double,
    val median: Double,
    val q3: Double,
    val max:Double
)


@Composable
fun RiskSelectionDisplay(
    riskData: List<RiskData>,
    onRiskSelected: (RiskData) -> Unit,
    surveyViewModel: SurveyViewModel,
    navController: NavController
) {
    var currentRiskIndex by remember { mutableStateOf(0) }
    val currentRisk = riskData[currentRiskIndex]

    Column(
        modifier = Modifier
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant)))
            .fillMaxSize()

    ) {
        Text(
            "Select Your Risk Level",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )



        Spacer(modifier = Modifier.height(24.dp))

        KeyStatisticsCard(currentRisk)

        Spacer(modifier = Modifier.height(16.dp))

        ReturnRangeCard(surveyViewModel, currentRisk)

        Spacer(modifier = Modifier.height(16.dp))

        RiskDescriptionCard(currentRisk)

        Spacer(modifier = Modifier.height(16.dp))


//        AdditionalStatisticsCard(currentRisk)


        Spacer(modifier = Modifier.height(24.dp))

        RiskLevelSelector(
            currentRiskLevel = currentRisk.level,
            onPrevious = { if (currentRiskIndex > 0) currentRiskIndex-- },
            onNext = { if (currentRiskIndex < riskData.size - 1) currentRiskIndex++ }
        )
        Spacer(modifier = Modifier.height(16.dp))
        FunctionButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                surveyViewModel.saveAnswer(6, currentRisk.level)
            onRiskSelected(currentRisk)
                      },
            text ="Confirm ${currentRisk.level} Selection" ,
            buttonWidth = 400.dp
        )


        Spacer(modifier = Modifier.weight(1f))

        BottomNavigation(navController)
    }
}

@Composable
fun KeyStatisticsCard(riskData: RiskData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatColumn("Annual Return", riskData.meanYield, Color.Green)
            VerticalDivider()
            StatColumn("Volatility", riskData.stdDev, Color(0xFFFFA500))
        }
    }
}

@Composable
fun StatColumn(label: String, value: Double, color: Color) {
    var showInfoDialog by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = { showInfoDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = color
                )
            }
        }
        Text(
            "${"%.2f".format(value)}%",
            style = MaterialTheme.typography.headlineMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }

    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {
                Text(text = "What is $label?")
            },
            text = {
                Text(
                    text = when (label) {
                        "Volatility" -> "Volatility measures the risk of your investment, indicating how much the value of the asset fluctuates over time."
                        // Add more cases for other labels if needed
                        else -> "Annual return refers to the percentage gain or loss of an investment over a one-year period," +
                                " showing how much the investment has increased or decreased in value annually."
                    }
                )
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun VerticalDivider() {
    Divider(
        color = Color.LightGray,
        modifier = Modifier
            .height(60.dp)
            .width(1.dp)
    )
}



@Composable
fun RiskDescriptionCard(riskData: RiskData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Risk Level: ${riskData.level}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MyColors.Primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when (riskData.level) {
                    "Low Risk" -> "Conservative approach with lower potential returns but more stability."
                    "Medium Risk" -> "Balanced approach with moderate potential returns and volatility."
                    "High Risk" -> "Aggressive approach with higher potential returns but increased volatility."
                    else -> "Unknown risk level"
                },
                color = Color.Black
            )
        }
    }
}

@Composable
fun AdditionalStatisticsCard(riskData: RiskData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Additional Statistics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MyColors.Primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatItem("Lowest Return", riskData.min)
            StatItem("Highest Return", riskData.max)
//            StatItem("Lower Quartile Return", riskData.q1)
//            StatItem("Median Return", riskData.median)
//            StatItem("Upper Quartile Return", riskData.q3)
        }
    }
}

@Composable
fun StatItem(label: String, value: Double) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Text(
            text = "${"%.2f".format(value)}%",
            modifier = Modifier.weight(0.3f),
            color = MyColors.Primary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun RiskLevelSelector(
    currentRiskLevel: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPrevious) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous", tint = Color.White)
        }
        Text(
            currentRiskLevel,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = onNext) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next", tint = Color.White)
        }
    }
}



@Composable
fun ReturnRangeCard(surveyViewModel: SurveyViewModel,riskData: RiskData) {

    val returnAmount = surveyViewModel.getAnswer(1).ifEmpty { "25000" }
    var numberOfYears = surveyViewModel.getAnswer(5).ifEmpty { "4" }

    numberOfYears = when (numberOfYears) {
        "0-2" -> "2"
        "2-4" -> "4"
        "4-40" -> "40"
        else -> numberOfYears
    }

    val minRange = ((riskData.min / 100 + 1).pow( numberOfYears.toDouble())) * returnAmount.toDouble()
    val maxRange = ((riskData.max / 100 + 1) .pow( numberOfYears.toDouble())) * returnAmount.toDouble()
    val numberFormat = NumberFormat.getNumberInstance(Locale.US)
    numberFormat.maximumFractionDigits = 2
    numberFormat.minimumFractionDigits = 2

    val formattedMinRange = numberFormat.format(minRange)
    val formattedMaxRange = numberFormat.format(maxRange)
    val formattedReturnAmount = numberFormat.format(returnAmount.toDouble())



    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors =CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),

        ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Investment Amount: $formattedReturnAmount",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
            text = "Projected Returns for ${riskData.level}  over $numberOfYears Years:",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Expected Return Range: \n$formattedMinRange - $formattedMaxRange",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
        }
    }


//@Composable
//@Preview
//fun RiskSelectionDisplayPrev() {
//    val riskData = listOf(
//        RiskData("Low Risk", 0.41, 6.82, -14.80, -1.18, 0.19, 1.06, 18.98),
//        RiskData("Medium Risk", 1.91, 17.73, -30.32, -1.84, 0.51, 1.93, 46.00),
//        RiskData("High Risk", 3.51, 25.46, -37.50, -2.50, 0.42, 2.82, 67.67)
//    )
//    val viewModel = SurveyViewModel()
//    RiskSelectionDisplay(
//        riskData = riskData,
//        onRiskSelected = { selectedRisk ->
//            // Handle the selected risk level
//            println("Selected risk level: ${selectedRisk.level}")
//        },
//        viewModel
//    )
//
//}


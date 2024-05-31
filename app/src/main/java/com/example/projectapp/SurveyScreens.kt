package com.example.projectapp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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




@Composable
fun SurveyScreen(navController: NavHostController, questionNumber: Int, question: String) {
    var answer by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        Text(
            text = question,
            color = Color.White,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FunctionButton(
                modifier = Modifier,
                onClick = { if (questionNumber > 1) navController.navigate("question${questionNumber - 1}") },
                text = "Back",
                width = 130.dp,
                enabled = questionNumber > 1  // Disable the button for the first question
            )


            if (questionNumber < 6) {
                FunctionButton(
                    modifier = Modifier,
                    onClick = { navController.navigate("question${questionNumber + 1}") },
                    text = "Next",
                    width = 120.dp
                )
            } else {
                FunctionButton(
                    modifier = Modifier,
                    onClick = { navController.navigate("summary") },
                    text = "Finish",
                    width = 130.dp
                )

            }
        }
    }
}

@Composable
fun SummaryScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Thank you for completing the survey!", style = MaterialTheme.typography.labelMedium)
        Button(onClick = { navController.navigate("HomeScreen") }) {
            Text("Back Home")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        SurveyScreen(navController,1,"Thank you for completing the survey!")
    }
    
}
package com.example.projectapp

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.theme.ProjectAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}
object MyColors {
    val Primary = Color(0xFF6200EE)
    val ButtonColor = Color(0xFF7C24F8)
    val PrimaryVariant = Color(0xFF3700B3)
    val Secondary = Color(0xFF03DAC6)
    val SecondaryVariant = Color(0xFF018786)

}

@Composable
fun Title(modifier: Modifier=Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
        ) {
        Text(
            modifier=Modifier,
            text = "ROBO\n  ADVISOR",
            fontSize = 32.sp,
            color = Color.White
        )
    }
}
@Composable
fun WelcomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyColors.Primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),

    ){
        Title(modifier)
        Spacer(modifier = modifier.height(16.dp))
        Box(
            modifier = modifier.fillMaxHeight(0.5f),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "Welcome to new world investing",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(26.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = MyColors.ButtonColor
            ),
            onClick = { /*TODO*/ },
            ) {
            Text(
                text = "Login",
                modifier=modifier.padding(16.dp))
        }
        Spacer(modifier = modifier.height(32.dp))
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Don't have an account?",
                color = Color.White,
                fontSize = 16.sp
            )
            Button(
                modifier = modifier.paddingFromBaseline(16.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = MyColors.ButtonColor
                ),
            ) {
                Text(
                    text = "Register Now",
                    fontSize = 12.sp
                )
            }
        }


    }

}
@Preview
@Composable
fun WelcomePagePreview() {
    ProjectAppTheme {
        WelcomePage()
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ProjectAppTheme {
//        Greeting("Android")
//    }
//}
package com.example.projectapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme

object MyColors {
    val Primary = Color(0xff5c1cec)
    val ButtonColor = Color(0xff6c2cf4)
    val PrimaryVariant = Color(0xFF290086)
    val Secondary = Color(0xFF03DAC6)
    val SecondaryVariant = Color(0xFF018786)

}

@Composable
fun Title(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier= Modifier,
            text = "ROBO\n  ADVISOR",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun FunctionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textSize:TextUnit = 24.sp,
    textColor: Color = Color.White,
    containerColor: Color = Color.Transparent,
    gradientColors: List<Color> = listOf(MyColors.Primary, MyColors.PrimaryVariant),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    buttonPadding: PaddingValues = PaddingValues(16.dp),
    shape: RoundedCornerShape = RoundedCornerShape(32.dp),
    width: Dp = 150.dp,
    enabled:Boolean = true
) {
    val backgroundColors = if (enabled) {
        Brush.horizontalGradient(enabledBackgroundColors)
    } else {
        Brush.horizontalGradient(listOf(disabledBackgroundColor, disabledBackgroundColor))
    }
    ElevatedButton(
        modifier = modifier
            .width(width)
            .padding(buttonPadding),
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            containerColor = containerColor
        ),
        contentPadding = PaddingValues(),
        shape = shape,
        onClick = onClick,
        enabled = enabled
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                modifier = modifier.padding(contentPadding),
                fontSize = textSize,
                color = textColor
            )
        }
    }
}


@Composable
fun FooterCreateAccount(modifier: Modifier = Modifier,navController: NavController) {
    Divider(
        color = Color.White.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(32.dp)
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "Don't have an account?",
            color = Color.White,
            fontSize = 18.sp
        )

        TextButton(
            modifier = modifier,
            onClick = {navController.navigate("SignupScreen")},
        ){
            Text(
                text = "Register Now",
                color = Color.White,
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            )
        }
    }
    Spacer(modifier = modifier.height(46.dp))
}
@Composable
fun WelcomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ){
        Title(modifier)
        Spacer(modifier = modifier.height(16.dp))
        Box(
            modifier = modifier.fillMaxHeight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Welcome to",
                    color = Color.White,
                    fontSize = 24.sp
                )
                Text(
                    text = "New World",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Investing",
                    fontSize= 34.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        FunctionButton(
            modifier = modifier,
            onClick = { navController.navigate("LoginScreen")},
            text = "Login"
        )
        Spacer(modifier = modifier.height(32.dp))
        FooterCreateAccount(modifier,navController)


    }

}
@Preview
@Composable
fun WelcomeScreenPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        WelcomeScreen(navController)
    }
}

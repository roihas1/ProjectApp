package com.example.projectapp

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.theme.ProjectAppTheme

object MyColors {
    val Primary = Color(0xff5c1cec)
    val ButtonColor = Color(0xff6c2cf4)
    val PrimaryVariant = Color(0xFF3700B3)
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
    onClick : () -> Unit,
    text: String
){
    ElevatedButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(26.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = onClick,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                modifier = modifier.padding(16.dp),
                fontSize = 24.sp
            )
        }
    }
    
}

@Composable
fun FooterCreateAccount(modifier: Modifier = Modifier) {
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
            onClick = {/*todo*/},
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
fun WelcomeScreen(modifier: Modifier = Modifier) {
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
            onClick = {/*todo*/},
            text = "Login"
        )
        Spacer(modifier = modifier.height(32.dp))
        FooterCreateAccount(modifier)


    }

}
@Preview
@Composable
fun WelcomeScreenPreview() {
    ProjectAppTheme {
        WelcomeScreen()
    }
}

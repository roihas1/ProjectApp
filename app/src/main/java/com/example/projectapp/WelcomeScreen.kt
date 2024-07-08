package com.example.projectapp

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.ui.theme.ProjectAppTheme
import com.example.projectapp.viewmodel.AuthViewModel
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

object MyColors {
    val Primary = Color(0xff5c1cec)
    val ButtonColor = Color(0xff6c2cf4)
    val PrimaryVariant = Color(0xFF290086)
    val Secondary = Color(0xFFFFFFFF)
    val SecondaryVariant = Color(0xFF4D50FF)

}

@Composable
fun Title(
    modifier: Modifier = Modifier,
    cardHeight: Dp = 80.dp,
    cardWidth: Dp = 250.dp,
    topSpace: Dp = 16.dp
) {
    Card(
        modifier = modifier
            .padding(top = 30.dp, start = 4.dp, end = 4.dp, bottom = 4.dp)
            .width(cardWidth)
            .height(cardHeight),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 26.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_1),
                contentDescription = "Robo Advisor Logo",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }

}


@Composable
fun FunctionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    buttonWidth: Dp = 150.dp,
    textSize: TextUnit = 24.sp,
    enabledTextColor: Color = Color.White,
    disabledTextColor: Color = Color.Gray,
    enabledBackgroundColors: List<Color> = listOf(MyColors.Primary, MyColors.PrimaryVariant),
    disabledBackgroundColor: Color = Color.DarkGray,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    buttonPadding: PaddingValues = PaddingValues(8.dp),
    shape: RoundedCornerShape = RoundedCornerShape(26.dp),
    enabled: Boolean = true,
    withIcon:Boolean=false
) {
    var isFilled by remember { mutableStateOf(false) }
    val iconRotation by animateFloatAsState(if (isFilled) 360f else 0f, animationSpec = spring(),
        label = ""
    )
    val backgroundColors = if (enabled) {
        Brush.horizontalGradient(enabledBackgroundColors)
    } else {
        Brush.horizontalGradient(listOf(disabledBackgroundColor, disabledBackgroundColor))
    }

    ElevatedButton(
        modifier = modifier
            .width(buttonWidth)
            .padding(buttonPadding),
        colors = ButtonDefaults.buttonColors(
            contentColor = if (enabled) enabledTextColor else disabledTextColor,
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        shape = shape,
        onClick = {
            onClick()
            if (withIcon) {
                isFilled = !isFilled
            }
        },
        enabled = enabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColors),
            contentAlignment = Alignment.Center
        ) {
            if(withIcon){
                val rotationState = remember { mutableStateOf(0f) }
                Row(
                    modifier = Modifier.padding(contentPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = text,
                        color = if (enabled) enabledTextColor else disabledTextColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = if (isFilled) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFilled) "Filled Heart" else "Heart Outline",
                        tint = if (enabled) enabledTextColor else disabledTextColor,
                        modifier = Modifier.graphicsLayer(rotationZ = iconRotation)
                    )
                }

            }
            else {
                Text(
                    text = text,
                    modifier = Modifier.padding(contentPadding),
                    fontSize = textSize,
                    color = if (enabled) enabledTextColor else disabledTextColor,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}


@Composable
fun FooterCreateAccount(modifier: Modifier = Modifier,navController: NavController,viewModel: AuthViewModel) {
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
            onClick = {
                viewModel.resetState()
                navController.navigate("SignupScreen")
                      },
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
fun WelcomeScreen(navController: NavController, viewModel: AuthViewModel,modifier: Modifier = Modifier) {
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
        FooterCreateAccount(modifier,navController, viewModel  )


    }

}
@Composable
fun CrazyAnimatedText(text: String, modifier: Modifier = Modifier) {


    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Magenta, Color.Cyan, Color.Yellow)
    var currentColorIndex by remember { mutableStateOf(0) }
    val targetColor by remember { derivedStateOf { colors[currentColorIndex] } }

    val color by animateColorAsState(targetColor, animationSpec = tween(1000))

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentColorIndex = (currentColorIndex + 1) % colors.size
        }
    }

    Row(modifier = modifier) {
        text.forEachIndexed { index, char ->
            val bounceState = remember { Animatable(0f) }

            LaunchedEffect(char) {
                while (true) {
                    bounceState.animateTo(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    bounceState.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    delay(Random.nextLong(500, 2500))
                }
            }

            val yOffset by bounceState.asState()

            Text(
                text = char.toString(),
                color = color,

                fontSize = 36.sp,
                modifier = Modifier.offset(y = (-20 * yOffset).dp)
            )
        }
    }
}

// Usage
@Preview
@Composable
fun CrazyTextDemo() {
    CrazyAnimatedText(
        text = "ROBO ADVISOR",
        modifier = Modifier.padding(16.dp)
    )
}
@Preview
@Composable
fun TitlePreview(){
    ProjectAppTheme {
        Title()
    }
}
@Preview
@Composable
fun WelcomeScreenPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        val viewModel = AuthViewModel()
        WelcomeScreen(navController,viewModel)
    }
}

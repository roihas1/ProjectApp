package com.example.projectapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.theme.ProjectAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column ( modifier = modifier
        .fillMaxSize()
        .background(Brush.verticalGradient(listOf(MyColors.Primary, MyColors.PrimaryVariant))),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(52.dp)
    ){
        Title()

       Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(24.dp)
                    .width(320.dp),
                shape = MaterialTheme.shapes.extraLarge,
                contentPadding = PaddingValues(48.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MyColors.ButtonColor,
                    containerColor = MyColors.ButtonColor
                )
            ) {
                Text(
                    modifier= Modifier.padding(16.dp),
                    text="Create New\n\nInvestment\n\nPortfolio",
                    fontSize = 32.sp,
                    color= Color.White,
                    textAlign = TextAlign.Center
                )
            }

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(16.dp)
                    .width(320.dp),
                shape = MaterialTheme.shapes.large,
                contentPadding = PaddingValues(48.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MyColors.ButtonColor,
                    containerColor = MyColors.ButtonColor
                )
            ) {
                Text(
                    modifier= Modifier.padding(18.dp),
                    text="Go To Last\n\n Portfolio",
                    fontSize = 32.sp,
                    color= Color.White,
                    textAlign = TextAlign.Center
                )
            }


    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    ProjectAppTheme {
        HomeScreen()
    }

}
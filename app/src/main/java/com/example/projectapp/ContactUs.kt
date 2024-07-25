package com.example.projectapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ContactUsScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MyColors.Primary,
                        MyColors.PrimaryVariant
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Enable scrolling if content overflows
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Contact Us",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 36.dp)
            )
            CustomTextField(
                label = "Name",
                onValueChange = { name = it },
                value = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
            )
            CustomTextField(
                label = "Your email",
                onValueChange = { email = it },
                value = email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
            )
            CustomTextField(
                label = "Message",
                onValueChange = { message = it },
                value = message,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Make the message text field bigger
                    .padding(bottom = 24.dp),

            )

            Row {
                FunctionButton(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        navController.popBackStack()
                              },
                    text = "Back",
                    buttonWidth = 120.dp,
                    textSize = 18.sp
                )
                FunctionButton(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("roi.hass1@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "Contact Us Message from $name")
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Name: $name\nReply Email: $email\n\nMessage:\n$message"
                            )
                        }
                        try {
                            navController.context.startActivity(
                                Intent.createChooser(
                                    intent,
                                    "Send Email"
                                )
                            )
                        } catch (ex: ActivityNotFoundException) {
                            // Handle the case where no email app is available
                            Toast.makeText(
                                navController.context,
                                "No email client installed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    text = "Send",
                    buttonWidth = 200.dp,

                )

            }

        }

        BottomNavigation(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedAbout = true
        )
    }
}


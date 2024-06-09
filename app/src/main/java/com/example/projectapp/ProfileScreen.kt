package com.example.projectapp
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectapp.ui.theme.ProjectAppTheme
import com.example.projectapp.viewmodel.AuthViewModel
import org.json.JSONObject

@Composable
fun ProfileScreen(navController: NavController, viewModel: AuthViewModel, modifier: Modifier = Modifier){
    var showDialog by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("User Name") }
    var userImageUri by remember { mutableStateOf<Uri?>(null) }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MyColors.Primary,
                            MyColors.PrimaryVariant
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                FunctionButton(
                    modifier = Modifier,
                    onClick = {
                        viewModel.resetState()
                        navController.navigate("welcomeScreen")
                              },// todo insert logout functionality
                    text = "Logout",
                    buttonWidth = 120.dp,
                    textSize = 16.sp
                )
                Icon(
                    Icons.Rounded.Settings,
                    contentDescription = "Settings",
                    Modifier
                        .padding(horizontal = 18.dp)
                        .size(48.dp)
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = {/*todo*/}),
                    tint = Color.White,
                    )
            }
            Row(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                horizontalArrangement = Arrangement.Center
            ) {

                Column(
                    modifier= modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (userImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(userImageUri)
                                    .build()
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.profile_missing),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                        )
                    }
                    FunctionButton(
                        modifier = Modifier,
                        onClick = { showDialog = true },
                        text = "Edit",
                        buttonWidth = 80.dp,
                        textSize = 16.sp
                    )
                }
            }
            Text(
                text = viewModel.username,
                modifier = modifier
                    .padding(16.dp),
                style = TextStyle(fontSize = 24.sp),
                color = Color.White
            )
            Text(
                text = viewModel.email,
                modifier = modifier
                    .padding(16.dp),
                style = TextStyle(fontSize = 24.sp),
                color = Color.White
            )
            FunctionButton(
                modifier = Modifier,
                onClick = { navController.navigate("changePassword") },
                text = "Change password",
                buttonWidth = 360.dp,

            )
            FunctionButton(
                modifier = Modifier,
                onClick = { navController.navigate("question1") },
                text = "New Investment Portfolio",
                buttonWidth = 360.dp,

            )

            BottomNavigation(navController, modifier)
            if (showDialog) {
                EditProfileDialog(
                    currentName = userName,
                    currentImageUri = userImageUri,
                    onDismiss = { showDialog = false },
                    onSave = { newName, newImageUri ->
                        userName = newName
                        userImageUri = newImageUri
                        showDialog = false
                    }
                )
            }
        }
    }




@Preview
@Composable
fun ProfileScreenPreview() {
    ProjectAppTheme {
        val navController = rememberNavController()
        val viewModel = AuthViewModel()
        ProfileScreen(navController,viewModel)
    }

}

//
//@Composable
//fun PokemonInfoScreen() {
//    var pokemonInfo by remember { mutableStateOf("") }
//
//    val context = LocalContext.current
//
//    // Call fetchPokemonInfo when the Composable is first composed
//    LaunchedEffect(Unit) {
//        fetchPokemonInfo(context, pokemonId = 1,
//            onSuccess = { info ->
//                pokemonInfo = info
//            },
//            onError = { errorMessage ->
//                pokemonInfo = errorMessage
//            }
//        )
//    }
//
//    Column {
//        Text(text = "Pokemon Information:")
//        Text(text = pokemonInfo)
//        Button(onClick = {
//            // Call fetchPokemonInfo again when the button is clicked
//            fetchPokemonInfo(context, pokemonId = 2,
//                onSuccess = { info ->
//                    pokemonInfo = info
//                },
//                onError = { errorMessage ->
//                    pokemonInfo = errorMessage
//                }
//            )
//        }) {
//            Text(text = "Fetch Different Pokémon")
//        }
//    }
//}
//fun fetchPokemonInfo(context: Context, pokemonId: Int, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
//    // Instantiate the RequestQueue.
//    val requestQueue = Volley.newRequestQueue(context)
//
//    // URL of the API endpoint for the specified Pokémon ID.
//    val url = "https://catfact.ninja/fact"
//
//    // Request a string response from the provided URL.
//    val stringRequest = StringRequest(Request.Method.GET, url,
//        { response ->
//            try {
//                // Parse the JSON response
//                val jsonObject = JSONObject(response)
//                val fact = jsonObject.getString("fact")
//                val length = jsonObject.getString("length")
//
//
//
//                // Format the information
//                val formattedInfo = "fact: ${fact}, length: ${length}"
//
//                // Call the onSuccess callback with the formatted information
//                onSuccess.invoke(formattedInfo)
//            } catch (e: Exception) {
//                // Handle JSON parsing error
//                onError.invoke("Error parsing JSON: ${e.message}")
//            }
//        },
//        { error ->
//            // Call the onError callback with the error message
//            Log.println(Log.ERROR,"APII","errorrrrr: ${error.message}")
//            onError.invoke("Error: ${error.message}")
//
//        })
//
//    // Add the request to the RequestQueue.
//    requestQueue.add(stringRequest)
//}
//@Preview(showBackground = true)
//@Composable
//fun PokemonInfoScreenPreview() {
//    ProjectAppTheme {
//        PokemonInfoScreen()
//    }
//}
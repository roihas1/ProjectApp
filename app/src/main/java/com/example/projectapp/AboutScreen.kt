package com.example.projectapp

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AboutScreen(navController: NavController) {
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Align content to the top
        ) {
            Text(
                text = "About the App",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Welcome to our recommendation portfolio app. " +
                        "Our goal is to provide you the best portfolio for you!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TeamSection()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "We are Software and Information Systems engineers with expertise in Machine Learning and a passion for investments." +
                        " We aim to bridge the gap in stock market knowledge, providing everyone with the chance to learn and invest.",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "What is a Portfolio?",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "A portfolio is a collection of financial investments like stocks, bonds, commodities, cash, and cash equivalents, including mutual funds and ETFs. Portfolios are held directly by investors and/or managed by financial professionals.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Why is it Important to Invest?",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Investing is important because it helps you grow your wealth, meet your financial goals, and protect against inflation. By investing wisely, you can build a more secure financial future, ensuring that your money works for you over time.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Through our app, you can answer a series of questions to help us" +
                        " tailor your investment portfolio to your specific needs and risk tolerance. " +
                        "This personalized approach ensures that your investments align with your financial goals and preferences.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        BottomNavigation(
            navController = navController,
            selectedAbout = true,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
@Composable
fun TeamSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Team",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TeamGrid(
            members = listOf(
                "Roy Turiski" to "https://www.linkedin.com/in/roy-turiski/",
                "Ofer Waldmann" to "https://www.linkedin.com/in/ofer-waldmann-2325b6176/",
                "Erez Kimmel" to "https://www.linkedin.com/in/erez-kimmel/",
                "Roi Hass" to "https://www.linkedin.com/in/roi-hass/"
            )
        )
    }
}

@Composable
fun TeamGrid(members: List<Pair<String, String>>) {
    val chunkedMembers = members.chunked(2)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        chunkedMembers.forEach { rowMembers ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowMembers.forEachIndexed { index, (name, linkedInUrl) ->
                    TeamMemberCard(name = name, linkedInUrl = linkedInUrl)
                    if (index < rowMembers.size - 1) {
                        VerticalDivider(
                            modifier = Modifier
                                .height(60.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeamMemberCard(name: String, linkedInUrl: String) {
    val context = LocalContext.current
    val imageName = when {
        name.startsWith("Ofer", ignoreCase = true) -> R.drawable.ofer_image
        name.startsWith("Erez", ignoreCase = true) -> R.drawable.erez_image
        name.startsWith("Roi", ignoreCase = true) -> R.drawable.roi_image
        else -> R.drawable.roy_image
    }

    Card(
        modifier = Modifier
            .width(177.dp)
            .height(60.dp)
            .clickable {
                val customTabsIntent = CustomTabsIntent
                    .Builder()
                    .build()
                customTabsIntent.launchUrl(context, Uri.parse(linkedInUrl))
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0x33FFFFFF)
        ),
        shape = RoundedCornerShape(30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageName),
                contentDescription = "$name's photo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement  = Arrangement.Center
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Box(modifier= Modifier.padding(start = 8.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.linkedin_logo),
                        contentDescription = "LinkedIn Logo",
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun VerticalDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(1.dp)
            .background(Color.White.copy(alpha = 0.5f))
    )
}



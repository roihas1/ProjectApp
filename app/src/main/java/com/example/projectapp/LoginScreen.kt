package com.example.projectapp

import android.graphics.drawable.Icon
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectapp.ui.theme.ProjectAppTheme


@Composable
fun CustomTextField(
    modifier: Modifier=Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
) {
    TextField(
        value ="" ,
        onValueChange = onValueChange,
        label = { Text(text) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        shape = MaterialTheme.shapes.large,
    )
    
}
@Composable
fun LoginScreen(
    modifier: Modifier=Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyColors.Primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Title()
        Text(
            text = "Login to continue",
            color = Color.White,
            fontSize = 24.sp
        )
        CustomTextField(
            text = "Username",
            onValueChange = {/*TODO*/},
            icon = Icons.Default.Person
        )
        CustomTextField(
            text = "Password",
            onValueChange = {/*TODO*/},
            icon = Icons.Default.Lock
        )

        FunctionButton(
            text = "Login",
            onClick = {/*TODO*/}
        )
        Spacer(modifier = modifier.height(48.dp))
        FooterCreateAccount(modifier)
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ProjectAppTheme {
        LoginScreen()
    }

}
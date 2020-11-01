package com.example.inchat.ui.registration_compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.example.inchat.R
import com.example.inchat.view_model.RegistrationViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    chatScreen: () -> Unit,
    signIn: () -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }

    Surface(color = Color.Black) {
        Column(
            modifier = Modifier.padding(16.dp).background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                asset = vectorResource(id = R.drawable.ic_anonymous_mask),
                modifier = Modifier.size(160.dp).clip(CircleShape).padding(20.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text(text = "Enter name") },
                inactiveColor = Color.White,
                activeColor = Color.Green,
                textStyle = TextStyle(color = Color.Green)
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text(text = "Enter email") },
                keyboardType = KeyboardType.Email,
                inactiveColor = Color.White,
                activeColor = Color.Green,
                textStyle = TextStyle(color = Color.Green)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text(text = "Enter password") },
                keyboardType = KeyboardType.Password, inactiveColor = Color.White,
                activeColor = Color.Green,
                textStyle = TextStyle(color = Color.Green)
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                }, label = { Text(text = "Confirm password") }, inactiveColor = Color.White,
                activeColor = Color.Green,
                textStyle = TextStyle(color = Color.Green)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(modifier = Modifier.width(200.dp),
                backgroundColor = Color.Black,
                border = BorderStroke(color = Color.White, width = 2.dp),
                onClick = {
                    if (email.text.isNotEmpty() && name.text.isNotEmpty() && password.text == confirmPassword.text) {
                        viewModel.register.registerUser(
                            chat = chatScreen,
                            email = email.text,
                            userName = name.text,
                            password = password.text
                        )
                    }
                }) {
                Text(text = "ENTER", fontSize = 20.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(modifier = Modifier.width(200.dp),
                backgroundColor = Color.Black,
                border = BorderStroke(color = Color.White, width = 2.dp),
                onClick = signIn
            ) {
                Text(text = "SIGN IN", fontSize = 20.sp, color = Color.White)
            }
        }
    }

}
package com.example.inchat.ui.signin_compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inchat.R
import com.example.inchat.view_model.SignInViewModel

//@Preview(showBackground = true)
@Composable
fun SignInScreen(viewModel: SignInViewModel, signUp: () -> Unit, chatScreen: () -> Unit) {
    Surface(color = Color.Black) {
        var email by remember { mutableStateOf(TextFieldValue()) }
        var password by remember { mutableStateOf(TextFieldValue()) }
        Column(
            modifier = Modifier.padding(16.dp).background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                }, label = { Text(text = "Enter email") }, inactiveColor = Color.White,
                activeColor = Color.Green,
                textStyle = TextStyle(color = Color.Green)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text(text = "Enter password") },
                keyboardType = KeyboardType.Password,
                inactiveColor = Color.White,
                activeColor = Color.Green,
                textStyle = TextStyle(color = Color.Green)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(modifier = Modifier.width(200.dp),
                backgroundColor = Color.Black,
                border = BorderStroke(color = Color.White, width = 2.dp), onClick =
                {
                    if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                         viewModel.signIn(email = email.text,password = password.text,chat = {chatScreen()})
                    }
                }) {
                Text(text = "Sign In", fontSize = 20.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.padding(10.dp))

            Button(modifier = Modifier.width(200.dp),
                backgroundColor = Color.Black,
                border = BorderStroke(color = Color.White, width = 2.dp),
                onClick = {signUp() }) {
                Text(text = "Registration", fontSize = 20.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.padding(15.dp))

            Image(
                asset = vectorResource(id = R.drawable.ic_keyboard),
                alignment = Alignment.BottomCenter,
                modifier = Modifier.size(300.dp)
            )
        }
    }
}
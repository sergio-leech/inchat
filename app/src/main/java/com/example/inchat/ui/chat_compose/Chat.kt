package com.example.inchat.ui.chat_compose

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.inchat.model.Message

@Composable
fun Chat(name: String, list: List<Message>) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        InchatAppBar(title = { Text(text = name, color = Color.White) })
        Spacer(modifier = Modifier.padding(10.dp))
        LazyColumnFor(items = list, modifier = Modifier.height(500.dp)) { message ->
          //  MessageItem(content = message)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.background(Color.Black)) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.size(300.dp).wrapContentHeight(),
                inactiveColor = Color.White,
                activeColor = Color.Green,
                visualTransformation = VisualTransformation.None,
                textStyle = TextStyle(color = Color.Green),
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                onImeActionPerformed = { action, softwareController ->
                    if (action == ImeAction.Done) {
                        softwareController?.hideSoftwareKeyboard()
                    }
                }

            )
            Button(
                backgroundColor = Color.Black,
                onClick = {},
            ) {
                Text(text = "GO", color = Color.Green)
            }
        }

    }


}

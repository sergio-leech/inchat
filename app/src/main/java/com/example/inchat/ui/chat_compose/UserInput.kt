package com.example.inchat.ui.chat_compose

import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun UserInput(onTextChanged: (TextFieldValue) -> Unit,
              textFieldValue: TextFieldValue,sendMessage:(String)->Unit){
    Row() {
        TextField(
            value =  textFieldValue,
            onValueChange = { onTextChanged(textFieldValue) },
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
            onClick = {sendMessage(textFieldValue.text)},
            modifier = Modifier.drawShadow(
                2.dp,
                CircleShape
            ).padding(2.dp).border(2.dp, Color.White, CircleShape)
        ) {
            Text(text = "GO", color = Color.Green)
        }
    }

}
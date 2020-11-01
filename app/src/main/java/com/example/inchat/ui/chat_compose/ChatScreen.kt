package com.example.inchat.ui.chat_compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inchat.R
import com.example.inchat.model.User
import com.example.inchat.ui.users_compose.randomColor
import com.example.inchat.view_model.ChatViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
/*
@Composable
fun ChatScreen(chatViewModel: ChatViewModel, backBtn: () -> Unit, sendMessage: () -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue()) }
  //  var keyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }
    Scaffold(
        backgroundColor = Color.Black, topBar = {
            TopAppBar(backgroundColor = Color.Black) {
                IconButton(onClick = backBtn) {
                    Image(asset = vectorResource(id = R.drawable.arrow_back_24))
                }
                Avatar(user = chatViewModel.currentUser)
            }
        }, bottomBar = {
            BottomAppBar(
                backgroundColor = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Row() {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.size(300.dp),
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
                        onClick = sendMessage,
                        modifier = Modifier.drawShadow(
                            2.dp,
                            CircleShape
                        ).padding(2.dp).border(2.dp, Color.White, CircleShape)
                    ) {
                        Text(text = "GO", color = Color.Green)
                    }
                }

            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumnFor(
                modifier = Modifier.fillMaxWidth(),
                items = listOf(
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy",
                    "hello",
                    "How ere you?",
                    "Are you busy"
                )
            ) { mesage ->
                Text(
                    text = mesage,
                    color = Color.Green*//*,modifier = Modifier.fillMaxWidth()*//*,
                    textAlign = TextAlign.Left
                )
            }
           *//* Row() {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.size(300.dp),
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
                    onClick = sendMessage,
                    modifier = Modifier.drawShadow(
                        2.dp,
                        CircleShape
                    ).padding(2.dp).border(2.dp, Color.White, CircleShape)
                ) {
                    Text(text = "GO", color = Color.Green)
                }
            }*//*

        }
    }
}*/

@Composable
private fun UserInputText(
    text: TextFieldValue,
    sendMessage: () -> Unit,
    CircleShape: RoundedCornerShape
) {
    var text1 = text
    Row(modifier = Modifier.background(Color.Black)) {
        TextField(
            value = text1, onValueChange = { text1 = it },
            inactiveColor = Color.White,
            activeColor = Color.Green,
            textStyle = TextStyle(color = Color.Green)
        )
        Button(
            backgroundColor = Color.Black, onClick = sendMessage, modifier = Modifier.drawShadow(
                2.dp,
                CircleShape
            ).padding(2.dp).border(2.dp, Color.White, CircleShape)
        ) {
            Text(text = "GO", color = Color.Green)
        }
    }
}


@Composable
fun Avatar(user: User?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val ringColor = remember { randomColor() }

        Text(text = user?.userName.toString(), color = Color.White, fontSize = 25.sp)
        Spacer(modifier = Modifier.padding(15.dp))
        when (user?.userImage) {
            "" -> Image(
                asset = vectorResource(id = R.drawable.ic_anonymous_mask),
                modifier = Modifier
                    .border(2.dp, ringColor, CircleShape)
                    .padding(10.dp)
                    .clip(CircleShape)
                    .size(55.dp)
            )
            else -> {
                CoilImage(
                    data = user?.userImage ?: "",
                    modifier = Modifier
                        .border(2.dp, ringColor, CircleShape)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .size(55.dp)

                )
            }
        }
    }
}
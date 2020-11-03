package com.example.inchat.ui.profile_compose

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadFontResource
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inchat.R
import com.example.inchat.model.User
import com.example.inchat.ui.users_compose.randomColor
import com.example.inchat.view_model.ProfileViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    arrowBackClick: () -> Unit,
    exitBtn: () -> Unit,
    selectImage: () -> Unit
) {
    Scaffold(backgroundColor = Color.Black, topBar = {
        TopAppBar(backgroundColor = Color.Black) {

            IconButton(onClick = arrowBackClick) {
                Image(
                    asset = vectorResource(id = R.drawable.ic_back_filled_arrow),
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(onClick = exitBtn) {
                Text(text = "EXIT", fontSize = 20.sp, color = Color.White)
            }

        }
    }) {
        Column(
            modifier = Modifier.padding(start = 85.dp, 16.dp).background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileAvatar(user = viewModel.user, selectImage = selectImage)
            Spacer(modifier = Modifier.padding(10.dp))
            Button(modifier = Modifier.width(200.dp),
                backgroundColor = Color.Black,
                border = BorderStroke(color = Color.White, width = 2.dp), onClick = {}) {
                Text(text = "Save", fontSize = 20.sp, color = Color.White)
            }

        }
    }
}

@Composable
fun ProfileAvatar(user: User?,selectImage:()->Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val ringColor = remember { randomColor() }
            when (user?.userImage) {
                "" -> Image(
                    asset = vectorResource(id = R.drawable.ic_anonymous_mask),
                    modifier = Modifier
                        .border(2.dp, ringColor, CircleShape)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .size(200.dp)
                        .clickable(onClick = selectImage)
                )
                else -> {
                    CoilImage(
                        data = user?.userImage?:"",
                        modifier = Modifier
                            .border(2.dp, ringColor, CircleShape)
                            .padding(10.dp)
                            .clip(CircleShape)
                            .size(200.dp)
                            .clickable(onClick =selectImage )
                    )
                }
            }


        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = user?.userName?:"", color = Color.White, fontSize = 40.sp)
    }
}


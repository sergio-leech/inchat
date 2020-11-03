package com.example.inchat.ui.users_compose

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.inchat.model.User
import com.example.inchat.view_model.UsersViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun UserChat(viewModel: UsersViewModel, userProfile: () -> Unit) {
    CoilImage(
        data = { },
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(100.dp, 100.dp).background(color = Color.Blue, shape = CircleShape)
            .clickable(
                onClick = userProfile
            )
    )
    Column(modifier = Modifier.padding(top = 40.dp)) {
        LazyColumnFor(items = viewModel.userList) { user ->
            UserItem(user = user)
        }
    }

}

@Composable
fun UserItem(user: User) {
    Row(modifier = Modifier.padding(5.dp)) {
        CoilImage(
            data = user.userImage,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp, 100.dp)
                .background(color = Color.Blue, shape = CircleShape)
        )
        Text(text = user.userName)
    }
}



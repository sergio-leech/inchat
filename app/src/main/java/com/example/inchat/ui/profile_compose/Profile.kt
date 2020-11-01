package com.example.inchat.ui.profile_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.res.loadVectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.example.inchat.R
import com.example.inchat.model.User
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Profile(prevPage: () -> Unit, user: User) {


    Column() {
        val image = loadVectorResource(id = R.drawable.arrow_back_24)
        image.resource.resource?.let { icon ->
            Image(
                asset = icon,
                modifier = Modifier.padding(top = 10.dp, start = 15.dp).size(40.dp, 40.dp)
                    .background(
                        Color.Green
                    )
                    .clickable(onClick = prevPage)
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))

        CoilImage(
            data = user.userImage,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp, 100.dp)
                .background(color = Color.Blue, shape = CircleShape).padding(top = 20.dp)

        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = user.userName, fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PrevProfile() {
    val user = User("Sergio Leech", "")
    Profile(prevPage = {}, user = user)
}
package com.example.inchat.ui.users_compose

import android.view.View
import androidx.compose.foundation.*
import com.example.inchat.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.inchat.model.User
import com.example.inchat.view_model.UsersViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlin.random.Random

@Composable
fun UsersScreen(viewModel: UsersViewModel, userProfile: () -> Unit,view: View) {
           val user = viewModel.userCurrent
        Scaffold(
            backgroundColor = Color.Black,
            topBar = {
                TopAppBar(backgroundColor = Color.Black, elevation = 4.dp) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Avatar(user =user, profile = userProfile)

                    }
                }
            },
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
                val userList= viewModel.userList
                ScrollableColumn(modifier = Modifier.fillMaxWidth()) {
                    userList.forEach { user->
                        UserItem(user = user,id = user.userId,view = view,userName =user.userName  )
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
}

@Composable
fun UserItem(user: User,id:String,view:View,userName:String) {
    Row(
        modifier = Modifier.background(Color.Black).padding(start = 10.dp, top = 5.dp).clickable(
            onClick = { navigationToChat(view = view,id = id,currentUserName = userName)}
        )
    ) {
        UserPhoto(photo = user.userImage)
        Spacer(modifier = Modifier.padding(10.dp))
        Column() {
            Row(modifier = Modifier.background(Color.Black)) {
                Text(text = user.userName, color = Color.White, fontSize = 20.sp)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "")
            }
            Surface(color = Color.Black) {
                Text(text = "", color = Color.Green)
            }
        }
    }
}

@Composable
fun UserPhoto(photo: String) {
    val ringColor = remember { randomColor() }
    when (photo) {
        "" -> Image(
            asset = vectorResource(id = R.drawable.ic_anonymous_mask),
            modifier = Modifier
                .border(2.dp, ringColor, CircleShape)
                .padding(5.dp)
                .clip(CircleShape)
                .size(45.dp)
        )
        else -> {
            CoilImage(
                data = photo,
                modifier = Modifier
                    .border(2.dp, ringColor, CircleShape)
                    .padding(5.dp)
                    .clip(CircleShape)
                    .size(45.dp)

            )
        }
    }

}
@Composable
fun Avatar(user:User?, profile:()->Unit){
    Row(verticalAlignment = Alignment.CenterVertically) {
        val ringColor = remember { randomColor() }
        when (user?.userImage) {
            "" -> Image(
                asset = vectorResource(id = R.drawable.ic_anonymous_mask),
                modifier = Modifier
                    .border(2.dp, ringColor, CircleShape)
                    .padding(10.dp)
                    .clip(CircleShape)
                    .size(55.dp).clickable(onClick =profile )
            )
            else -> {
                CoilImage(
                    data = user?.userImage?:"",
                    modifier = Modifier
                        .border(2.dp, ringColor, CircleShape)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .size(55.dp)
                        .clickable(onClick = profile)
                )
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = user?.userName.toString(), color = Color.White,fontSize = 25.sp)
    }
}



fun randomColor():Color= Color(
    red = Random.nextInt(0 ,255),
    green = Random.nextInt(0,255),
    blue = Random.nextInt(0,255)
)

fun navigationToChat(id:String,view:View,currentUserName:String){
    val bundle = bundleOf("userId" to id,"userName" to currentUserName)
    view.findNavController().navigate(R.id.action_chatFragment_to_chatFragment2,bundle)
}
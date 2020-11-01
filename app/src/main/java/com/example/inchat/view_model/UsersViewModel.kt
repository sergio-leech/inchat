package com.example.inchat.view_model

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inchat.firebase.FirebaseUtil
import com.example.inchat.firebase.State
import com.example.inchat.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class UsersViewModel @ViewModelInject constructor(private val firebaseUtil: FirebaseUtil) :
    ViewModel() {
    private val firebaseAuth = Firebase.auth
    private val databaseReference = Firebase.database.getReference("Users")
    val register = firebaseUtil
    val data = Firebase.database.reference.child("Users")

    //  var userList = firebaseUtil.getUserList()
    /*var userList: List<User> by mutableStateOf(listOf())
        private set

    init {
        usersList()
    }

    private fun usersList() {
        viewModelScope.launch {
            userList = userList + register.getUserList()
        }
    }
*/

     var userList: List<User> by mutableStateOf(listOf())
         private set
   // val value by flow.collectAsState()

    var userCurrent: User? by mutableStateOf(User())
        private set

     init {
        getList()
     }
    
  /* private fun getList(){
        viewModelScope.launch {
         val userList=  register.getUsers()
            val value by userList.collectAsState(initial = listOf<User?>())

        }
    }*/

     private fun getList() {
         val currentUser = firebaseAuth.currentUser
         databaseReference
             .addValueEventListener(object : ValueEventListener {
                 override fun onCancelled(error: DatabaseError) {
                     error.message
                 }

                 override fun onDataChange(dataSnapshot: DataSnapshot) {
                     userList = listOf()
                     dataSnapshot.children.forEach { data ->
                         val user = data.getValue(User::class.java)
                         if (!user?.userId.equals(currentUser?.uid)) {
                             user?.let {
                                 userList = userList + listOf(user)

                             }
                         }else{
                             userCurrent= user
                         }
                     }
                 }

             })
     }
}

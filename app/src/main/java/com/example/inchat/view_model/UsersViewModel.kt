package com.example.inchat.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.inchat.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class UsersViewModel @ViewModelInject constructor() : ViewModel() {
    private val firebaseAuth = Firebase.auth
    private val databaseReference = Firebase.database.getReference("Users")
    val data = Firebase.database.reference.child("Users")

    var userList: List<User> by mutableStateOf(listOf())
        private set
    var userCurrent: User? by mutableStateOf(User())
        private set

    init {
        getList()
    }

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
                            user?.let { userItem ->
                                userList = userList + listOf(userItem)
                            }
                        } else {
                            userCurrent = user
                        }
                    }
                }
            })
    }
}

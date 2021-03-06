package com.example.inchat.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.inchat.firebase.FirebaseUtil
import com.example.inchat.model.User
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileViewModel @ViewModelInject constructor(private val _firebaseUtil: FirebaseUtil) :
    ViewModel() {
    private val firebaseAuth = Firebase.auth
    private val databaseReference = Firebase.database.getReference("Users")

    var user: User? by mutableStateOf(User())
        private set

    init {
        getUser()
    }

    private fun getUser() {
        val userId = firebaseAuth.currentUser?.uid.toString()
        databaseReference.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapsShot: DataSnapshot) {
                user = dataSnapsShot.getValue(User::class.java)
            }

            override fun onCancelled(dataError: DatabaseError) {
                dataError.message
            }
        })
    }

    fun exitFromProfile(signIn: () -> Unit) {
        _firebaseUtil.exitFromProfile { signIn() }
    }
}
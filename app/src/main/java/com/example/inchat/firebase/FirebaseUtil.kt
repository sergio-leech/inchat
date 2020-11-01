package com.example.inchat.firebase

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import com.example.inchat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUtil @Inject constructor() {

    private val firebaseAuth = Firebase.auth
    private val databaseReference = Firebase.database.getReference("Users")


    fun registerUser(userName: String, email: String, password: String, chat: () -> Unit) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { result ->
                    if (result.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid.toString()
                        val hashMap: HashMap<String, String> = HashMap()
                        hashMap.apply {
                            put("userId", userId)
                            put("userName", userName)
                            put("profileImage", "")
                        }
                        databaseReference.child(userId).setValue(hashMap)
                            .addOnCompleteListener() { save ->
                                if (save.isSuccessful) {
                                    // Open Chat Screen
                                    chat()
                                }
                            }
                    }
                }
        } catch (e: Exception) {
            e.message
        }

    }

    fun autoLogin(chat: () -> Unit) {
        Log.d("Tag", "Firebase current user${firebaseAuth.currentUser?.uid}")
        if (firebaseAuth.currentUser != null) {
            chat()
        }
    }

    fun exitFromProfile(signIn: () -> Unit) {
        firebaseAuth.signOut()
        signIn()
    }

    //Suspend
    fun signIn(email: String, password: String, chat: () -> Unit) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        chat()
                    }
                }
        } catch (e: Exception) {
            e.message
        }
    }

    fun getUser() {
        val userId = firebaseAuth.currentUser?.uid.toString()
        databaseReference.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapsShot: DataSnapshot) {

            }

            override fun onCancelled(dataError: DatabaseError) {
                dataError.message
            }

        })

    }

    fun getUserList(): List<User> {
        var users: List<User> by mutableStateOf(listOf())
        val currentUser = firebaseAuth.currentUser

        databaseReference
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    error.message
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    users = listOf()
                    dataSnapshot.children.forEach { data ->
                        val user = data.getValue(User::class.java)
                        if (!user?.userId.equals(currentUser?.uid)) {
                            user?.let {
                                users = users + listOf(user)

                            }
                        }
                    }
                }

            })
        Log.d("TAG", "USERS $users")
        return users
    }
}
package com.example.inchat.firebase


import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUtil @Inject constructor() {

    private val firebaseAuth = Firebase.auth
    private val databaseReference = Firebase.database

    suspend fun registerUser(userName: String, email: String, password: String, chat: () -> Unit) {
        val authResult = registrationWithEmailAndPassword(email = email, password = password)
        if (authResult) {
            val saveData = saveDataInDatabaseReference(userName = userName)
            if (saveData) {
                val updateProfile = updateProfile(userName = userName)
                if (updateProfile) {
                    chat()
                }
            }
        }
    }

    private suspend fun updateProfile(userName: String): Boolean {
        return try {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build()
            firebaseAuth.currentUser?.updateProfile(profileUpdates)?.await()
            true
        } catch (e: Exception) {
            e.message
            false
        }
    }

    private suspend fun registrationWithEmailAndPassword(email: String, password: String): Boolean {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            e.message
            false
        }
    }

    private suspend fun saveDataInDatabaseReference(userName: String): Boolean {
        val userId = firebaseAuth.currentUser?.uid.toString()
        val hashMap: HashMap<String, String> = HashMap()
        return try {
            hashMap.apply {
                put("userId", userId)
                put("userName", userName)
                put("profileImage", "")
            }
            databaseReference.getReference("Users").child(userId).setValue(hashMap).await()
            true
        } catch (e: Exception) {
            e.message
            false
        }
    }

    fun autoLogin(chat: () -> Unit) {
        firebaseAuth.currentUser?.let {
            chat()
        }
    }

    fun exitFromProfile(signIn: () -> Unit) {
        firebaseAuth.currentUser?.delete()
        firebaseAuth.signOut()
        signIn()
    }

    suspend fun signIn(email: String, password: String, chat: () -> Unit) {
        val signInRes = enter(email = email, password = password)
        if (signInRes) {
            chat()
        }
    }

    private suspend fun enter(email: String, password: String): Boolean {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            e.message
            false
        }
    }
}
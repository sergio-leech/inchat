package com.example.inchat.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inchat.firebase.FirebaseUtil
import kotlinx.coroutines.launch

class SignInViewModel @ViewModelInject constructor(private val firebaseUtil: FirebaseUtil):ViewModel() {

    fun autoLogin(chat: () -> Unit){
        firebaseUtil.autoLogin {
            chat()
        }
    }

    fun signIn(email: String, password: String, chat: () -> Unit){
        viewModelScope.launch {
            firebaseUtil.signIn(email = email,password = password,chat = chat)
        }
    }
}
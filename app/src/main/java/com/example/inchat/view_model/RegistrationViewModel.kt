package com.example.inchat.view_model


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inchat.firebase.FirebaseUtil
import kotlinx.coroutines.launch

class RegistrationViewModel @ViewModelInject constructor(private val firebaseUtil: FirebaseUtil) :
    ViewModel() {

    fun registrationUser(userName: String, email: String, password: String, chat: () -> Unit){
        viewModelScope.launch{
            firebaseUtil.registerUser(userName = userName,email = email,password = password,chat = chat)
        }
    }
}
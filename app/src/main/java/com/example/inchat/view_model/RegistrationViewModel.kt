package com.example.inchat.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inchat.firebase.FirebaseUtil
import com.example.inchat.model.User
import kotlinx.coroutines.launch

class RegistrationViewModel @ViewModelInject constructor(private val firebaseUtil: FirebaseUtil) :
    ViewModel() {

    val register = firebaseUtil
   /* private  val _userList = MutableLiveData<List<User>>()
    val userList:LiveData<List<User>> = _userList*/
   /* var userList :List<User> by mutableStateOf(listOf())
        private set

    init {
        viewModelScope.launch {
            userList = firebaseUtil.getUserList()
            Log.d("TAG","LIST $userList")
        }
    }*/
}
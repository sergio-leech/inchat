package com.example.inchat.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inchat.model.Message
import com.example.inchat.model.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

class ChatViewModel @ViewModelInject constructor(@Assisted savedStateHandle: SavedStateHandle) :
    ViewModel() {
    val id = savedStateHandle.get<String>("userId")

    private val firebaseAuth = Firebase.auth.currentUser?.uid
    private val databaseReference = Firebase.database

    /* var currentUser: User? by mutableStateOf(User())
         private set*/

    private var _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser


    private var _listMessage = MutableLiveData<List<Message>?>()
    val listMessage: LiveData<List<Message>?> = _listMessage

    init {
        getUser()
        readMessage()
    }

    private fun getUser() {
        databaseReference.getReference("Users").child(id.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    _currentUser.postValue(user)


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    databaseError.message
                }

            })
    }

    fun sendMessage(message: String) {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap.apply {
            put("senderId", firebaseAuth.toString())
            put("receiverId", id.toString())
            put("message", message)
        }
        databaseReference.reference.child("Chat").push().setValue(hashMap)

    }

   private fun readMessage() {
        val senderId = firebaseAuth
        val receiverId = id
        val list: MutableList<Message>? = ArrayList()

        databaseReference.getReference("Chat").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                error.message
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                 list?.clear()
                snapshot.children.forEach { dataSnapshot ->
                    val chat = dataSnapshot.getValue(Message::class.java)
                    if (chat?.senderId.equals(senderId) && chat?.receiverId.equals(receiverId) ||
                        chat?.senderId.equals(receiverId) && chat?.receiverId.equals(senderId)
                    ) {
                       if(chat != null){
                           list?.add(chat)
                       }
                        _listMessage.postValue(list?.toList())
                    }
                }
            }

        })
    }
}
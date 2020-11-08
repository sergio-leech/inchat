package com.example.inchat.view_model


import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.inchat.model.Message
import com.example.inchat.model.NotificationData
import com.example.inchat.model.PushNotificationMessage
import com.example.inchat.model.User
import com.example.inchat.notification.NotificationApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatViewModel @ViewModelInject constructor(
    private val notificationApi: NotificationApi,
    @Assisted savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val id = savedStateHandle.get<String>("userId")
    private val userName = savedStateHandle.get<String>("userName")

    private val firebaseAuth = Firebase.auth.currentUser

    private val databaseReference = Firebase.database

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
        viewModelScope.launch(Dispatchers.IO) { sendMessageInFirebase(message = message) }
    }

    private suspend fun sendMessageInFirebase(message: String) {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap.apply {
            put("senderId", firebaseAuth?.uid.toString())
            put("receiverId", id.toString())
            put("message", message)
        }
        databaseReference.reference.child("Chat").push().setValue(hashMap).await()
    }

    private fun readMessage() {
        val senderId = firebaseAuth?.uid
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
                        chat?.let { chatItem ->
                            list?.add(chatItem)
                        }
                        _listMessage.postValue(list?.toList())
                    }
                }
            }

        })
    }

    fun sendNotification(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val topic = "/topics/$id"
                val pushNotificationMessage = PushNotificationMessage(
                    NotificationData(
                        title = firebaseAuth?.displayName.toString(),
                        message = message
                    ), to = topic
                )
                val response = notificationApi.postNotification(message = pushNotificationMessage)
                if (response.isSuccessful) {
                    Log.d("TAG", "Send Notification Is Successful")
                } else {
                    Log.e("TAG", response.errorBody()!!.string())
                }
            } catch (e: Exception) {
                e.message
            }
        }
    }
}
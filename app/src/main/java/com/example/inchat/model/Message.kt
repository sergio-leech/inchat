package com.example.inchat.model

import androidx.compose.runtime.Immutable

@Immutable
data class Message(
    var senderId: String = "",
    var receiverId: String = "",
    var message: String = ""
)
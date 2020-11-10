package com.example.inchat.notification


import android.util.Log
import androidx.core.os.bundleOf
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseServiceNotification : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "TOKEN: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val args = bundleOf("userId" to remoteMessage.data["userId"])
        deliverNotification(
            title = remoteMessage.data["title"].toString(),
            text = remoteMessage.data["message"].toString(),
            args = args,
            context = applicationContext
        )
        createNotificationChannel("FirebaseChanelChat", applicationContext)
    }
}
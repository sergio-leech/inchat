package com.example.inchat.notification


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseServiceNotification : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        deliverNotification(
            remoteMessage.data["title"].toString(),
            remoteMessage.data["message"].toString(),
            applicationContext
        )

        createNotificationChannel("FirebaseChanelChat", applicationContext)
    }
}
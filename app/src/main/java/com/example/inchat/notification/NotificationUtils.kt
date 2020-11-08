package com.example.inchat.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.inchat.R
import com.example.inchat.ui.UsersFragment


const val NOTIFICATION_ID = 0
const val PRIMARY_CHANNEL_ID = "primary_notification_channel"

fun deliverNotification(title: String, text: String, context: Context) {
    val mNotificationManager: NotificationManagerCompat? =
        NotificationManagerCompat.from(context)
    val contentIntent = Intent(context, UsersFragment::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_message_24)
        .setContentTitle(title)
        .setContentText(text)
        .setContentIntent(contentPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
    mNotificationManager?.notify(NOTIFICATION_ID, builder.build())
}

fun createNotificationChannel(name: String, context: Context) {
    val mNotificationManager: NotificationManagerCompat? =
        NotificationManagerCompat.from(context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(
                PRIMARY_CHANNEL_ID,
                name,
                IMPORTANCE_HIGH
            ).apply {
                description = "Send notification from Firebase"
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
            }
        mNotificationManager?.createNotificationChannel(notificationChannel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun isChannelEnabled(channelId: String, applicationContext: Context): Boolean {
    val notificationManager = ContextCompat.getSystemService(
        applicationContext,
        NotificationManager::class.java
    ) as NotificationManager
    val notificationChannel: NotificationChannel? =
        notificationManager.getNotificationChannel(channelId)
    return notificationChannel?.importance != NotificationManager.IMPORTANCE_NONE
}

fun cancelNotification(context: Context) {
    val mNotificationManager: NotificationManagerCompat? =
        NotificationManagerCompat.from(context)
    mNotificationManager?.cancelAll()
}



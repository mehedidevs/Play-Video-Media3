package com.mehedi.tlecevideo.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mehedi.tlecevideo.MainActivity
import com.mehedi.tlecevideo.R
import com.mehedi.tlecevideo.utils.sendReminderNotification
import com.mehedi.tlecevideo.utils.toBitmap

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {

        val notificationManager = ContextCompat.getSystemService(
            applicationContext, NotificationManager::class.java
        ) as NotificationManager


        message.notification?.let { notification ->
            notificationManager.sendReminderNotification(
                applicationContext = applicationContext,
                channelId = applicationContext.getString(R.string.notification_channel_id),
                "${notification.title}",
                "${notification.body}"
            )
        }
        super.onMessageReceived(message)
    }


}

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

fun NotificationManager.sendReminderNotification(
    applicationContext: Context, channelId: String, contentTitle: String, contentText: String
) {

    val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.tlece_logo)


    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_REQUEST_CODE,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val builder =
        NotificationCompat.Builder(applicationContext, channelId).setContentTitle(contentTitle)
            .setContentText(contentText).setSmallIcon(R.drawable.ic_video_not)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(drawable?.toBitmap())
            ).setVibrate(longArrayOf(100, 1000, 200, 1000, 100))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent)
            .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

var NOTIFICATION_ID = System.currentTimeMillis().toInt()
var NOTIFICATION_REQUEST_CODE = 111
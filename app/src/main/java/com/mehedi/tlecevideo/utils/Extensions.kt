package com.mehedi.tlecevideo.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mehedi.tlecevideo.MainActivity
import com.mehedi.tlecevideo.R


fun Fragment.showToast(msg: String) {

    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()

}


fun Drawable.toBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
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
            .setContentText(contentText).setSmallIcon(R.drawable.ic_video_not).setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(drawable?.toBitmap())
            ).setVibrate(longArrayOf(100, 1000, 200, 1000, 100)).setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent)
            .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

var NOTIFICATION_ID = System.currentTimeMillis().toInt()
const val NOTIFICATION_REQUEST_CODE = 111
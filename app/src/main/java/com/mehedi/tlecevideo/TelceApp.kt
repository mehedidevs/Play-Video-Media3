package com.mehedi.tlecevideo

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mehedi.tlecevideo.work.RecurringAlarm
import com.mehedi.tlecevideo.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TelceApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        createNotificationsChannels()
        delayedInit()
    }


    private fun delayedInit() {
        applicationScope.launch {
            setupOneTimeWork()
            RecurringAlarm.setRecurringAlarm(this@TelceApp)
        }
    }


    private fun setupOneTimeWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<RefreshDataWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(oneTimeRequest)
    }

    private fun createNotificationsChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.notification_channel_id),
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                enableLights(true)
                lightColor = Color.GRAY
                enableVibration(true)
            }
            // Register the channel with the system
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }

    }


}
package com.mehedi.tlecevideo.work

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object RecurringAlarm {

    private val REFRESH_ALARM_ID = 101
    fun setRecurringAlarm(context: Context) {

        stopReminder(context)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, RefreshAlarmReceiver::class.java)

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                REFRESH_ALARM_ID,
                alarmIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val intervalMillis = 5 * 60 * 1000

        val startTime =
            System.currentTimeMillis() + intervalMillis - (System.currentTimeMillis() % intervalMillis)


        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            startTime,
            intervalMillis.toLong(),
            pendingIntent
        )
    }


    private fun stopReminder(
        context: Context,
        ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, RefreshAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                REFRESH_ALARM_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        }
        alarmManager.cancel(intent)
    }

}
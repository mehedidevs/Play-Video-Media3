package com.mehedi.tlecevideo.work

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RefreshAlarmReceiver : BroadcastReceiver() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onReceive(context: Context, intent: Intent) {

        applicationScope.launch {
            setupOneTimeWork(context)
        }


    }

    private fun setupOneTimeWork(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<RefreshDataWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(oneTimeRequest)
    }
}
package com.mehedi.tlecevideo.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mehedi.tlecevideo.data.repository.VideoRepository
import com.mehedi.tlecevideo.di.DiProviders


class RefreshDataWorker(
    private val appContext: Context,
    params: WorkerParameters
) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        val repository =
            VideoRepository(DiProviders.videoDAO(applicationContext), DiProviders.videoService())
        return try {
            repository.insertVideos()
            RecurringAlarm.setRecurringAlarm(appContext)
            Result.success()
        } catch (e: Exception) {
            Log.d("TAG", "doWork: ${e.message} ")
            Result.retry()
        }
    }
}
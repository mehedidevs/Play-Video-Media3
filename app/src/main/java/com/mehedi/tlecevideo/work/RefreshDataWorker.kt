package com.mehedi.tlecevideo.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mehedi.tlecevideo.di.DiProviders
import com.mehedi.tlecevideo.data.repository.VideoRepository


class RefreshDataWorker(

    appContext: Context,
    params: WorkerParameters
) :
    CoroutineWorker(appContext, params) {


    companion object {

        const val WORK_NAME = "RefreshDataWorker"
    }


    override suspend fun doWork(): Result {

        val repository =
            VideoRepository(DiProviders.videoDAO(applicationContext), DiProviders.videoService())
        return try {
            repository.insertVideos()
            Result.success()
        } catch (e: Exception) {
            Log.d("TAG", "doWork: ${e.message} ")

            Result.retry()
        }
    }
}
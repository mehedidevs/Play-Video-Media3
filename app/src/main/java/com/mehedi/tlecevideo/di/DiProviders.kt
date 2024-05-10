package com.mehedi.tlecevideo.di

import android.content.Context
import androidx.room.Room
import com.mehedi.tlecevideo.data.local.VideoDAO
import com.mehedi.tlecevideo.data.local.VideoDatabase
import com.mehedi.tlecevideo.data.remote.VideoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiProviders {


    companion object {

        private var videoApiService: VideoApiService? = null
        private var videoDao: VideoDAO? = null

        @Synchronized
        fun videoService(): VideoApiService {
            if (videoApiService == null) {
                videoApiService = Retrofit.Builder()
                    .baseUrl(DiConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(VideoApiService::class.java)
                return videoApiService as VideoApiService
            } else {
                return videoApiService as VideoApiService
            }
        }

        @Synchronized
        fun videoDAO(context: Context): VideoDAO {

            if (videoDao == null) {
                videoDao =
                    Room.databaseBuilder(context, VideoDatabase::class.java, DiConstant.VIDEO_DB)
                        .build()
                        .getVideoDao()

                return videoDao as VideoDAO
            } else {
                return videoDao as VideoDAO
            }


        }
    }


}
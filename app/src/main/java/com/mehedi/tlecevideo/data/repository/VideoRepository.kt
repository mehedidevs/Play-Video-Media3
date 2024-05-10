package com.mehedi.tlecevideo.data.repository


import com.mehedi.tlecevideo.data.local.VideoDAO
import com.mehedi.tlecevideo.data.remote.VideoApiService
import com.mehedi.tlecevideo.data.remote.toVideoItem
import com.mehedi.tlecevideo.utils.InsertionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException



class VideoRepository (
    private val videoDao: VideoDAO,
    private val service: VideoApiService
) {

    suspend fun insertVideos() {

        withContext(Dispatchers.IO) {
            try {
                val response = service.getVideos()
                if (response.isSuccessful) {
                    val videoItems = response.body()?.map { responseVideo ->
                        responseVideo.toVideoItem()
                    }
                    videoItems?.let {
                        videoDao.insertVideos(*it.toTypedArray())
                    }
                } else {
                    throw HttpException(response)
                }
            } catch (e: Exception) {
                throw InsertionException(
                    "Failed to insert videos",
                    e
                )
            }
        }


    }

    val getAllVideos=  videoDao.getAllVideos()


}
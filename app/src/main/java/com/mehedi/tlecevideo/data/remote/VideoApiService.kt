package com.mehedi.tlecevideo.data.remote

import com.mehedi.tlecevideo.data.remote.ApiEndpoint.ENDPOINT_VIDEOS
import retrofit2.Response
import retrofit2.http.GET

interface VideoApiService {

    @GET(ENDPOINT_VIDEOS)
    suspend fun getVideos(): Response<List<ResponseVideo>>


}
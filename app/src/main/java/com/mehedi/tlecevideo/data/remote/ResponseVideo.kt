package com.mehedi.tlecevideo.data.remote


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.mehedi.tlecevideo.data.local.VideoItem
import com.mehedi.tlecevideo.utils.Converters.fromTimestamp


@Keep
data class ResponseVideo(
    @SerializedName("author") val author: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("duration") val duration: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("isLive") val isLive: Boolean? = null,
    @SerializedName("subscriber") val subscriber: String? = null,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("uploadTime") val uploadTime: String? = null,
    @SerializedName("videoUrl") val videoUrl: String? = null,
    @SerializedName("views") val views: String? = null
)

fun ResponseVideo.toVideoItem(): VideoItem {
    return VideoItem().apply {
        author = this@toVideoItem.author
        description = this@toVideoItem.description
        duration = this@toVideoItem.duration
        id = this@toVideoItem.id?.toInt() ?: 0
        isLive = this@toVideoItem.isLive
        subscriber = this@toVideoItem.subscriber
        thumbnailUrl = this@toVideoItem.thumbnailUrl
        title = this@toVideoItem.title
        uploadTime = this@toVideoItem.uploadTime
        videoUrl = this@toVideoItem.videoUrl.toString()
        views = this@toVideoItem.views
        updatedTime = fromTimestamp(System.currentTimeMillis())
    }
}

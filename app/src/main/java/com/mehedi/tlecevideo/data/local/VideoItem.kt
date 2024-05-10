package com.mehedi.tlecevideo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_table")
data class VideoItem(
    @PrimaryKey()
    var id: Int = 0,
    var author: String? = null,
    var description: String? = null,
    var duration: String? = null,
    var isLive: Boolean? = null,
    var subscriber: String? = null,
    var thumbnailUrl: String? = null,
    var title: String? = null,
    var uploadTime: String? = null,
    var videoUrl: String = "",
    var views: String? = null,
    var updatedTime: String? = null
)

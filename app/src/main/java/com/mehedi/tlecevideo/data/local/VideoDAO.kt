package com.mehedi.tlecevideo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(vararg videos: VideoItem)

    @Query("SELECT * FROM video_table")
    fun getAllVideos(): LiveData<List<VideoItem>>


}
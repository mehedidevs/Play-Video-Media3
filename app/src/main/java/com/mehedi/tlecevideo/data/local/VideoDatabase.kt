package com.mehedi.tlecevideo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mehedi.tlecevideo.utils.Converters

@Database(entities = [VideoItem::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun getVideoDao(): VideoDAO


}
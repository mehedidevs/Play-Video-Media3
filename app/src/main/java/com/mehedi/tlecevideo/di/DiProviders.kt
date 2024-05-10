package com.mehedi.tlecevideo.di

import android.content.Context
import androidx.room.Room

import com.mehedi.tlecevideo.data.local.VideoDAO
import com.mehedi.tlecevideo.data.local.VideoDatabase
import com.mehedi.tlecevideo.data.remote.VideoApiService
import com.mehedi.tlecevideo.data.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiProviders {

    @Provides
    @Singleton
    fun retrofitProviders(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(DiConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun providesVideoApiService(retrofit: Retrofit.Builder): VideoApiService {
        return retrofit.build().create(VideoApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesRoom(@ApplicationContext context: Context): VideoDatabase {
        return Room.databaseBuilder(context, VideoDatabase::class.java, DiConstant.VIDEO_DB).build()
    }

    @Provides
    @Singleton
    fun providesVideoDao(db: VideoDatabase): VideoDAO {
        return db.getVideoDao()
    }

    @Provides
    @Singleton
    fun provideVideoRepository(dao: VideoDAO, service: VideoApiService): VideoRepository {
        return VideoRepository(dao, service)
    }


}
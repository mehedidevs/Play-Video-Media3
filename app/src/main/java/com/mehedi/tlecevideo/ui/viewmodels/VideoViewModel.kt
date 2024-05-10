package com.mehedi.tlecevideo.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehedi.tlecevideo.data.local.VideoItem
import com.mehedi.tlecevideo.data.repository.VideoRepository
import com.mehedi.tlecevideo.utils.DataState
import kotlinx.coroutines.launch


class VideoViewModel(videoRepository: VideoRepository) : ViewModel() {


    val videosLiveData = videoRepository.getAllVideos


    private val _currentVideo = MutableLiveData<DataState<VideoItem>>()
    val currentVideo: LiveData<DataState<VideoItem>>
        get() = _currentVideo

    fun setCurrentVideo(videoItem: VideoItem) {
        getSuggestedVideo(videoItem)
        _currentVideo.postValue(DataState.Success(videoItem))


    }

    private val _suggestedVideos = MutableLiveData<DataState<List<VideoItem>>>()
    val suggestedVideos: LiveData<DataState<List<VideoItem>>>
        get() = _suggestedVideos

    private fun getSuggestedVideo(videoItem: VideoItem) {

        viewModelScope.launch {
            val suggestedVideos: MutableList<VideoItem> = mutableListOf()
            videosLiveData.value?.forEach { video ->
                if (video != videoItem) {
                    suggestedVideos.add(video)
                }
                _suggestedVideos.postValue(DataState.Success(suggestedVideos))
            }


        }
    }


}





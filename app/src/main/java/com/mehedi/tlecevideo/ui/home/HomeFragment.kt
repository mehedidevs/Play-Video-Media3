package com.mehedi.tlecevideo.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mehedi.tlecevideo.R
import com.mehedi.tlecevideo.data.local.VideoItem
import com.mehedi.tlecevideo.data.repository.VideoRepository
import com.mehedi.tlecevideo.databinding.FragmentHomeBinding
import com.mehedi.tlecevideo.di.DiProviders
import com.mehedi.tlecevideo.ui.base.BaseFragment
import com.mehedi.tlecevideo.ui.viewmodels.VideoViewModel
import com.mehedi.tlecevideo.ui.viewmodels.VideoViewmodelFactory


class HomeFragment : BaseFragment<FragmentHomeBinding>(), VideoAdapter.VideoClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_home


    private val videoAdapter: VideoAdapter by lazy {
        VideoAdapter(this)
    }


    private val viewModel by activityViewModels<VideoViewModel> {
        VideoViewmodelFactory(
            VideoRepository(
                DiProviders.videoDAO(requireContext()),
                DiProviders.videoService()
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializer()

        getAllVideoObserver()

    }

    private fun initializer() {
        binding.rvVideos.adapter = videoAdapter
    }


    private fun getAllVideoObserver() {

        viewModel.videosLiveData.observe(viewLifecycleOwner) { videoList ->
            videoAdapter.submitList(videoList)
        }

    }


    override fun onThumbnailClick(videoItem: VideoItem) {
        viewModel.setCurrentVideo(videoItem)

        findNavController().navigate(
            R.id.action_homeFragment_to_videoPlayerFragment
        )

    }


}
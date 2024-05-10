package com.mehedi.tlecevideo.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mehedi.tlecevideo.R
import com.mehedi.tlecevideo.data.DataHelper
import com.mehedi.tlecevideo.data.local.VideoItem
import com.mehedi.tlecevideo.data.repository.VideoRepository
import com.mehedi.tlecevideo.databinding.FragmentHomeBinding
import com.mehedi.tlecevideo.ui.base.BaseFragment
import com.mehedi.tlecevideo.ui.viewmodels.VideoViewModel
import com.mehedi.tlecevideo.ui.viewmodels.VideoViewmodelFactory
import com.mehedi.tlecevideo.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), VideoAdapter.VideoClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_home


    private val videoAdapter: VideoAdapter by lazy {
        VideoAdapter(this)
    }


    private val viewModel by activityViewModels<VideoViewModel> {
        val repository =
            VideoRepository(DataHelper.videoDAO(requireContext()), DataHelper.videoService())
        VideoViewmodelFactory(repository)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializer()
        insertVideoObserver()
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

    private fun insertVideoObserver() {

        viewModel.insertionStatus.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                showToast(getString(R.string.videos_inserted_successfully))
            }
            result.onFailure { e ->
                showToast(getString(R.string.failed_to_insert_videos, e.message))
            }
        }

    }

    override fun onThumbnailClick(videoItem: VideoItem) {
        viewModel.setCurrentVideo(videoItem)

        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToVideoPlayerFragment()
        )

    }


}
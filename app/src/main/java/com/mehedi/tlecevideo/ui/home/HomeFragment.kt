package com.mehedi.tlecevideo.ui.home

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
import com.mehedi.tlecevideo.utils.PermissionUtils


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
        requestPermissions()
        initializer()
        getAllVideoObserver()

    }

    private fun initializer() {
        loading.show()
        binding.rvVideos.adapter = videoAdapter
    }


    private fun getAllVideoObserver() {
        viewModel.videosLiveData.observe(viewLifecycleOwner) { videoList ->
            videoAdapter.submitList(videoList)
            loading.dismiss()
        }

    }


    override fun onThumbnailClick(videoItem: VideoItem) {
        viewModel.setCurrentVideo(videoItem)
        findNavController().navigate(
            R.id.action_homeFragment_to_videoPlayerFragment
        )

    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            when {
                it.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false) -> {}
                it.getOrDefault(Manifest.permission.SCHEDULE_EXACT_ALARM, false) -> {}
                else -> PermissionUtils.showPermissionSettings(
                    binding.root,
                    requireActivity(),
                    getString(R.string.rationale_notification)
                )
            }
        }

    private fun requestPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                )
            )
        }
    }


}
package com.mehedi.tlecevideo.ui.player

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.ExoTrackSelection
import androidx.media3.ui.PlayerView
import com.mehedi.tlecevideo.R
import com.mehedi.tlecevideo.di.DiProviders
import com.mehedi.tlecevideo.data.local.VideoItem
import com.mehedi.tlecevideo.data.repository.VideoRepository
import com.mehedi.tlecevideo.databinding.FragmentVideoPlayerBinding
import com.mehedi.tlecevideo.ui.base.BaseFragment
import com.mehedi.tlecevideo.ui.home.VideoAdapter
import com.mehedi.tlecevideo.ui.viewmodels.VideoViewModel
import com.mehedi.tlecevideo.ui.viewmodels.VideoViewmodelFactory
import com.mehedi.tlecevideo.utils.DataState
import com.mehedi.tlecevideo.utils.showToast


@Suppress("OPT_IN_ARGUMENT_IS_NOT_MARKER")
@UnstableApi
class VideoPlayerFragment : BaseFragment<FragmentVideoPlayerBinding>(),
    VideoAdapter.VideoClickListener {

    private var playerView: PlayerView? = null
    private var exoPlayer: ExoPlayer? = null

    private var defaultRequestProperties: Map<String, String> = HashMap()
    private var userAgent: String = ""
    private var trackSelector: DefaultTrackSelector? = null

    private val viewModel by activityViewModels<VideoViewModel> {
        val repository =
            VideoRepository(DiProviders.videoDAO(requireContext()), DiProviders.videoService())
        VideoViewmodelFactory(repository)
    }


    private val videoAdapter: VideoAdapter by lazy {
        VideoAdapter(this)
    }

    override val layoutId: Int
        get() = R.layout.fragment_video_player


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializer()
        currentVideoObserver()
        suggestedVideoObserver()
    }

    private fun initializer() {



        binding.apply {
            playerView = videoPlayerView
            rvVideos.adapter = videoAdapter

            imgFullScreen.setOnClickListener {
                showToast(getString(R.string.full_screen_feature_will_be_coming_soon))
            }
        }

    }

    private fun suggestedVideoObserver() {

        viewModel.suggestedVideos.observe(viewLifecycleOwner) { status ->

            Log.d("TAG", "suggestedVideoObserver: ${status.data} ")

            when (status) {
                is DataState.Error -> showToast(getString(R.string.something_went_wrong_to_play_the_video))
                is DataState.Loading -> showToast(getString(R.string.video_loading))
                is DataState.Success -> {
                    videoAdapter.submitList(status.data)
                }
            }

        }

    }


    private fun currentVideoObserver() {
        viewModel.currentVideo.observe(viewLifecycleOwner) { status ->
            when (status) {
                is DataState.Error -> showToast(getString(R.string.something_went_wrong_to_play_the_video))
                is DataState.Loading -> showToast(getString(R.string.video_loading))
                is DataState.Success -> {
                    setupPlayer(status.data?.videoUrl)
                    binding.video = status.data
                }
            }

        }


    }

    private fun setupPlayer(videoUrl: String?) {
        resetPlayer()

        videoUrl?.let { video ->
            val httpDataSourceFactory: DataSource.Factory =
                DefaultHttpDataSource.Factory().setUserAgent(userAgent)
                    .setKeepPostFor302Redirects(true).setAllowCrossProtocolRedirects(true)
                    .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                    .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
                    .setDefaultRequestProperties(defaultRequestProperties)
            val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                requireContext(), httpDataSourceFactory
            )
            val mediaItem: MediaItem =
                MediaItem.Builder().setUri(video).setMimeType(MimeTypes.APPLICATION_MP4).build()

            val progressiveMediaSource: MediaSource =
                ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
            initializeWithExo(progressiveMediaSource)
        }
    }

    private fun resetPlayer() {
        exoPlayer?.let {
            it.stop()
            it.release()
        }


    }


    @OptIn(markerClass = [UnstableApi::class])
    private fun initializeWithExo(progressiveMediaSource: MediaSource) {


        val videoSelector: ExoTrackSelection.Factory = AdaptiveTrackSelection.Factory()
        trackSelector = DefaultTrackSelector(requireContext(), videoSelector)
        exoPlayer = ExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector!!)
            .setSeekForwardIncrementMs(SEEK_THRESHOLD)
            .setSeekBackIncrementMs(SEEK_THRESHOLD)
            .build()

        playerView?.let {
            it.player = exoPlayer
            it.keepScreenOn = true
        }


        exoPlayer?.let {
            it.setMediaSource(progressiveMediaSource)
            it.prepare()
            it.playWhenReady = true
        }
    }


    override fun onStop() {
        super.onStop()
        exoPlayer?.pause()
    }

    override fun onStart() {
        super.onStart()
        exoPlayer?.play()
    }

    override fun onThumbnailClick(videoItem: VideoItem) {
        viewModel.setCurrentVideo(videoItem)
    }


    companion object {

        const val SEEK_THRESHOLD = 5000L
    }

}
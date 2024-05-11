package com.mehedi.tlecevideo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mehedi.tlecevideo.data.local.VideoItem
import com.mehedi.tlecevideo.databinding.ItemVideoBinding

class VideoAdapter(val videoClickListener: VideoClickListener) :
    ListAdapter<VideoItem, VideoAdapter.VideoViewHolder>(VIDEO_COMPARATOR) {

    fun interface VideoClickListener {
        fun onThumbnailClick(videoItem: VideoItem)
    }


    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(videoItem: VideoItem) {
            binding.apply {
                video = videoItem
                imgThumbnail.setOnClickListener {
                    videoClickListener.onThumbnailClick(videoItem)
                }
            }


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {

        return VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)

        }
    }

    companion object {
        val VIDEO_COMPARATOR = object : DiffUtil.ItemCallback<VideoItem>() {
            override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem == newItem
            }
        }

    }
}
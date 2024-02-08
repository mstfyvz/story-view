package com.yavuzmobile.story.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yavuzmobile.story.data.StoryItem
import com.yavuzmobile.story.data.StoryItemType
import com.yavuzmobile.story.databinding.StoryPagerItemBinding

class StoryPagerAdapter(
    private val storyList: List<StoryItem>,
    private val setOnTouchListener: (isTouch: Boolean) -> Unit
) :
    RecyclerView.Adapter<StoryPagerAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(
        private val binding: StoryPagerItemBinding,
        private val setOnTouchListener: (isTouch: Boolean) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var isTouching = false

        fun bind(storyItem: StoryItem) {
            when (storyItem.type) {
                StoryItemType.IMAGE -> {
                    binding.storyImageView.visibility = View.VISIBLE
                    binding.storyVideoView.visibility = View.GONE

                    Glide.with(itemView.context)
                        .load(storyItem.url)
                        .into(binding.storyImageView)

                    binding.storyImageView.setOnTouchListener { _, event ->
                        onTouchEvent(event)
                        true
                    }
                }

                StoryItemType.VIDEO -> {
                    binding.storyImageView.visibility = View.GONE
                    binding.storyVideoView.visibility = View.VISIBLE

                    val videoUri = Uri.parse(storyItem.url)
                    binding.storyVideoView.setVideoURI(videoUri)
                    binding.storyVideoView.setMediaController(MediaController(itemView.context))

                    binding.storyVideoView.setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.start()
                    }

                    binding.storyVideoView.setOnTouchListener { _, event ->
                        onTouchEvent(event)
                        true
                    }

                }


                else -> {
                    // Belirtilen bir tür yoksa, uygun bir işlem yapabilirsiniz.
                }
            }
        }

        private fun onTouchEvent(event: MotionEvent) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isTouching = true
                    setOnTouchListener(isTouching)
                    if (binding.storyVideoView.isVisible) {
                        binding.storyVideoView.pause()
                    }
                }

                MotionEvent.ACTION_UP -> {
                    isTouching = false
                    setOnTouchListener(isTouching)
                    if (binding.storyVideoView.isVisible) {
                        binding.storyVideoView.start()
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StoryPagerItemBinding.inflate(inflater, parent, false)
        return StoryViewHolder(binding, setOnTouchListener)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(storyList[position])
    }

    override fun getItemCount(): Int = storyList.size

}

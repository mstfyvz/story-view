package com.yavuzmobile.story.adapter

import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yavuzmobile.story.data.StoryGroupItem
import com.yavuzmobile.story.databinding.StoryItemViewBinding

class StoryAdapterViewHolder(private val binding: StoryItemViewBinding, private val isShowLabel: Boolean, private val onClickListener: (item: StoryGroupItem) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StoryGroupItem) {

        val url: String? = if (item.url.isNullOrEmpty()) {
            item.listStory?.first()?.url
        } else {
            item.url
        }

        binding.storyGroupLabel.isVisible = isShowLabel
        binding.storyGroupAnimation.isOpened = item.isOpened ?: false

        if (item.isOpened != true) {
            binding.storyGroupAnimation.startAnimations()
        }

        binding.storyGroupLabel.text = item.label ?: ""

        try {
            Glide
                .with(itemView)
                .load("$url")
                .into(binding.storyGroupImage)
        } catch (e: Exception) {
            Log.e("Glide ERROR", e.toString())
        }

        binding.root.setOnClickListener {
            binding.storyGroupAnimation.isOpened = true
            onClickListener(item)
        }
    }

}
package com.yavuzmobile.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yavuzmobile.story.data.StoryGroupItem
import com.yavuzmobile.story.databinding.StoryItemViewBinding

class StoryAdapter(
    private val isShowLabel: Boolean,
    private val onClickListener: (item: StoryGroupItem) -> Unit
) : RecyclerView.Adapter<StoryAdapterViewHolder>() {

    private var storyList = mutableListOf<StoryGroupItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = StoryItemViewBinding.inflate(layoutInflater, parent, false)
        return StoryAdapterViewHolder(binding, isShowLabel, onClickListener)
    }

    override fun onBindViewHolder(holder: StoryAdapterViewHolder, position: Int) {
        val storyGroupItem = storyList[position]
        holder.bind(storyGroupItem)
    }

    override fun getItemCount(): Int = storyList.size

    fun updateData(newList: List<StoryGroupItem>) {
        val diffCallback = StoryDiffCallback(storyList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        storyList.clear()
        storyList.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }

}
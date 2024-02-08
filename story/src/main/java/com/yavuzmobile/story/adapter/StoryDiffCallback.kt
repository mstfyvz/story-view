package com.yavuzmobile.story.adapter

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.yavuzmobile.story.data.StoryGroupItem

@Keep
class StoryDiffCallback(
    private val oldList: List<StoryGroupItem>,
    private val newList: List<StoryGroupItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

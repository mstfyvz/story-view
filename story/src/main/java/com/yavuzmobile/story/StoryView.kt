package com.yavuzmobile.story

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yavuzmobile.story.adapter.StoryAdapter
import com.yavuzmobile.story.data.StoryGroupItem
import com.yavuzmobile.story.databinding.StoryContainerViewBinding

class StoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var storyAdapter: StoryAdapter
    private val storyList = ArrayList<StoryGroupItem>()
    private val binding: StoryContainerViewBinding = StoryContainerViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        initUI()
    }

    private fun initUI() {
        binding.storyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        storyAdapter = StoryAdapter(false, ::onClickStoryGroup)
        binding.storyRecyclerView.adapter = storyAdapter
    }

    fun setInitUI(storyList: List<StoryGroupItem>, isShowLabel: Boolean = false) {
        this.storyList.clear()
        this.storyList.addAll(storyList)

        storyAdapter = StoryAdapter(isShowLabel, ::onClickStoryGroup)
        binding.storyRecyclerView.adapter = storyAdapter
        storyAdapter.updateData(storyList)
    }

    private fun onClickStoryGroup(item: StoryGroupItem) {
        storyList.find { it == item }?.let {
            it.isOpened = true
            storyList.remove(it)
            storyList.add(it)
        }
        storyAdapter.updateData(storyList)
        binding.storyRecyclerView.layoutManager?.scrollToPosition(0)

        val dialog = StoryDialogFragment.newInstance(item)
        dialog.show((context as FragmentActivity).supportFragmentManager, StoryDialogFragment.TAG)
    }
}
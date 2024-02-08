package com.yavuzmobile.story

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.yavuzmobile.story.adapter.StoryPagerAdapter
import com.yavuzmobile.story.data.StoryGroupItem
import com.yavuzmobile.story.data.StoryItem
import com.yavuzmobile.story.databinding.StoryDialogViewBinding
import com.yavuzmobile.story.ext.parcelable

class StoryDialogFragment : DialogFragment() {

    private lateinit var binding: StoryDialogViewBinding
    private lateinit var storyPagerAdapter: StoryPagerAdapter

    private var oldPosition = 0
    private var currentPosition = 0

    private lateinit var storyGroupItem: StoryGroupItem

    private var animator: ValueAnimator? = null

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            resetProgressBars()
            startProgressBarAnimate(position)
        }
    }

    companion object {
        const val TAG = "StoryDialogFragment"
        private const val ARG_STORY_GROUP_ITEM = "argStoryGroupItem"

        fun newInstance(item: StoryGroupItem): StoryDialogFragment {
            val fragment = StoryDialogFragment()
            val args = Bundle()
            args.putParcelable(ARG_STORY_GROUP_ITEM, item)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StoryDialogViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {}
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyGroupItem = arguments?.parcelable<StoryGroupItem>(ARG_STORY_GROUP_ITEM) ?: return
        val storyList: List<StoryItem>? = storyGroupItem.listStory

        if (storyList.isNullOrEmpty()) {
            dismiss()
            return
        }

        addProgressBars(storyList)

        storyPagerAdapter = StoryPagerAdapter(storyList, ::setOnTouchListener)
        binding.viewPager.adapter = storyPagerAdapter
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)

        binding.closeButtonDialogFragment.setOnClickListener {
            dismiss()
        }
    }

    private fun addProgressBars(storyList: List<StoryItem>) {
        binding.progressBarContainer.removeAllViews()
        for (i in storyList.indices) {
            val progressBar = ProgressBar(requireContext(), null, android.R.attr.progressBarStyleHorizontal)
            val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            layoutParams.setMargins(8, 0, 8, 0)
            progressBar.layoutParams = layoutParams
            progressBar.max = 100
            binding.progressBarContainer.addView(progressBar)
        }
        currentPosition = 0
    }

    private fun resetProgressBars() {
        // Tüm ProgressBar'ları döngüyle kontrol ederek sıfırlama işlemlerini gerçekleştiriyoruz
        for (i in 0 until binding.progressBarContainer.childCount) {
            val progressBar = binding.progressBarContainer.getChildAt(i) as? ProgressBar
            progressBar?.apply {
                progress = 0 // İlerleme değerini sıfırla
                max = 0 // Maksimum değeri sıfırla (süreyi sıfırlar)
                // Gerekirse diğer özellikleri sıfırla
            }
        }
    }

    private fun startProgressBarAnimate(progressBarIndex: Int) {
        val progressBar = binding.progressBarContainer.getChildAt(progressBarIndex) as? ProgressBar
        progressBar?.let {
            it.max = 100
            animator = ValueAnimator.ofInt(0, it.max)
            animator?.setDuration(5000)
            animator?.addUpdateListener { animation ->
                it.progress = (animation.animatedValue as Int)
            }
            animator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    oldPosition = currentPosition
                    currentPosition++
                    if (storyGroupItem.listStory?.size == currentPosition) {
                        dismiss()
                        return
                    }
                    binding.viewPager.currentItem = currentPosition
                }
            })
            animator?.start()
        }
    }

    private fun setOnTouchListener(isTouching: Boolean) {
        if (isTouching) {
            animator?.pause()
        } else {
            animator?.resume()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }
}
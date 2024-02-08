package com.yavuzmobile.story.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class StoryGroupItem(
    val id: Int?,
    val url: String?,
    val label: String?,
    var isOpened: Boolean?,
    val listStory: List<StoryItem>?
): Parcelable
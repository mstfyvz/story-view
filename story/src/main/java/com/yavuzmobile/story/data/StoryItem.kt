package com.yavuzmobile.story.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class StoryItem(
    val url: String?,
    var isOpened: Boolean?,
    val type: StoryItemType?
): Parcelable
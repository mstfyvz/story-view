package com.yavuzmobile.storyapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.yavuzmobile.story.data.StoryGroupItem
import com.yavuzmobile.story.data.StoryItem
import com.yavuzmobile.story.data.StoryItemType
import com.yavuzmobile.storyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val storyList = listOf(
            StoryGroupItem(0, "", "", false, listOf(StoryItem("https://i.ibb.co/HD1wK2F/pexels-amaan-shaikh-19182572.jpg", false, StoryItemType.IMAGE), StoryItem("https://i.ibb.co/xsKRJ02/pexels-eberhard-grossgasteiger-2310713.jpg", false, StoryItemType.IMAGE))),
            StoryGroupItem(1, "", "Jeremy Group Label", false, listOf(StoryItem("https://i.ibb.co/8XQCT0c/pexels-jeremy-bishop-2397652.jpg", false, StoryItemType.IMAGE), StoryItem("https://i.ibb.co/tqTQVJk/pexels-karolina-grabowska-4622893.jpg", false, StoryItemType.IMAGE), StoryItem("https://i.ibb.co/9Gsxdyx/pexels-nothing-ahead-3571551.jpg", false, StoryItemType.IMAGE))),
            StoryGroupItem(2, "", "", false, listOf(StoryItem("https://i.ibb.co/9Gsxdyx/pexels-nothing-ahead-3571551.jpg", false, StoryItemType.IMAGE))),
            StoryGroupItem(3, "https://i.ibb.co/tqTQVJk/pexels-karolina-grabowska-4622893.jpg", "", false, listOf(StoryItem("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", false, StoryItemType.VIDEO)))
        )
        binding.storyView.setInitUI(storyList, true)
    }
}
package com.blackz.novella

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.blackz.novella.adapter.StoryPagerAdapter


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName
    private  lateinit var storyPager:ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        storyPager = findViewById(R.id.storyPager)
        storyPager.adapter = StoryPagerAdapter(this)


    }
}
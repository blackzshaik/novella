package com.blackz.novella.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blackz.novella.fragments.StoryViewFragment

class StoryPagerAdapter (activity:FragmentActivity):FragmentStateAdapter(activity){
    override fun getItemCount(): Int  = 3

    override fun createFragment(position: Int): Fragment {
        return StoryViewFragment()
    }

}
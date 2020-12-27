package com.blackz.novella.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.blackz.novella.MainActivity
import com.blackz.novella.R
import com.blackz.novella.customviews.NovellaProgress
import com.blackz.novella.listeners.ProgressListener
import com.blackz.novella.util.listOfVideos
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class StoryViewFragment : Fragment(),ProgressListener {


    private val TAG = this::class.simpleName
    private lateinit var novellaProgress: NovellaProgress

    private lateinit var thisFragment: View

    private lateinit var storyPlayer:SimpleExoPlayer
    private lateinit var playerView:PlayerView

    private var imVisible = false
    private var isStartedAlready = false

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(isStartedAlready){
            if (menuVisible) resumePlayer()
        }else{
            if(menuVisible){
                setupPlayer()
                setupNovellaProgress()
            }else{
                pausePlayer()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisFragment = inflater.inflate(R.layout.fragment_story_view, container, false)
        return thisFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = thisFragment.findViewById(R.id.playerView)

    }

    private fun setupPlayer(){
        storyPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        playerView.player = storyPlayer
        val mediaSource:MutableList<MediaItem> = mutableListOf()
        listOfVideos.forEach{
            mediaSource.add(MediaItem.fromUri(it))
        }
        storyPlayer.setMediaItems(mediaSource)
        storyPlayer.prepare()
        storyPlayer.play()
    }

    private fun pausePlayer(){
        novellaProgress.pause()
        storyPlayer.pause()
    }

    private fun releasePlayer(){

        storyPlayer.pause()
        storyPlayer.release()
        playerView.player = null
    }

    private fun setupNovellaProgress(){
        novellaProgress = thisFragment.findViewById(R.id.novellaProgress)

        novellaProgress.setupProgressListener(this)
        novellaProgress.setupView(listOfVideos.size)


        thisFragment.findViewById<AppCompatButton>(R.id.pauseButton).setOnClickListener{
            novellaProgress.pause()
        }
        thisFragment.findViewById<AppCompatButton>(R.id.playButton).setOnClickListener{
            novellaProgress.resume()
        }
        thisFragment.findViewById<AppCompatButton>(R.id.nextButton).setOnClickListener{
            novellaProgress.next()
        }
        thisFragment.findViewById<AppCompatButton>(R.id.previousButton).setOnClickListener{
            novellaProgress.previous()
        }
    }

    private fun resumePlayer(){
        novellaProgress.startFrom(1)
        storyPlayer.play()
    }

    override fun onNext() {
        storyPlayer.next()
    }

    override fun onPrevious() {
        storyPlayer.prepare()
    }

    override fun onEnd() {
        storyPlayer.stop()
    }

    override fun onPauseOrResume(isPlaying: Boolean) {
        if(isPlaying){
            storyPlayer.play()
        }else{
            storyPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        novellaProgress.destroy()
    }

}
package com.blackz.novella.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.blackz.novella.R
import com.blackz.novella.customviews.NovellaProgress
import com.blackz.novella.listeners.ProgressListener
import com.blackz.novella.model.MediaType
import com.blackz.novella.util.checkMediaType
import com.blackz.novella.util.hide
import com.blackz.novella.util.listOfVideos
import com.blackz.novella.util.show
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView


class StoryViewFragment : Fragment(),ProgressListener {


    private val TAG = this::class.simpleName
    private lateinit var novellaProgress: NovellaProgress

    private lateinit var thisFragment: View

    private lateinit var storyPlayer:SimpleExoPlayer
    private lateinit var playerView:PlayerView
    private lateinit var imageView:AppCompatImageView

    private var imVisible = false
    private var isStartedAlready = false

    private var centerOfControls = 0

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        imVisible = menuVisible
        if(isStartedAlready){
            if (menuVisible) resumePlayer()
        }else{
            if(menuVisible){
                setupNovella()
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
        imageView = thisFragment.findViewById(R.id.imageView)

        //todo; implement controls
        thisFragment.findViewById<View>(R.id.manualControls).setOnTouchListener(controlsTouchListener)

        val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        centerOfControls = display.width / 2
    }

    //todo;implement long press to pause and resume
    @SuppressLint("ClickableViewAccessibility")
    private val controlsTouchListener = View.OnTouchListener { v, event ->
        Log.d(TAG,"Inside contorts touch listener")
        Log.d(TAG,"some unkonw event ${event.action}")

        Log.d(TAG,"event.x == ${event.x} and event.y == ${event.y}")
        when(event.action){
            MotionEvent.ACTION_UP -> {
                Log.d(TAG,"MotionEvent.ActionUp")
                if(event.x < centerOfControls){
                    novellaProgress.previous()
                }else if (event.x > centerOfControls){
                    novellaProgress.next()
                }

            }
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG,"MotionEvent.ActionDown")

            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG,"now im moving")

            }
        }
        true
    }

    private fun setupNovella(){
        storyPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        playerView.player = storyPlayer
        storyPlayer.prepare()

        setupNovellaProgress()
        storyPlayer.addListener(playerEventListener)


    }


    private fun playVideo(which: Int){
        playerView.show()
        imageView.hide()


        val mediaItem = MediaItem.fromUri(listOfVideos[which].url)
//        listOfVideos.forEach{
//            if(it.url.checkMediaType() == MediaType.VIDEO){
//                mediaSource.add(MediaItem.fromUri(it.url))
//            }
//
//        }
        storyPlayer.setMediaItem(mediaItem)

        storyPlayer.play()


    }

    private fun showImage(which:Int){
        novellaProgress.pause()
        playerView.hide()
        imageView.show()
        Glide.with(imageView)
             .load(listOfVideos[which].url)
                .listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        novellaProgress.next()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        novellaProgress.resume()
                        return false
                    }
                })
             .into(imageView)
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
    }

    override fun onPause() {
        super.onPause()
        if(imVisible){
            pausePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if(imVisible){
            resumePlayer()
        }
    }

    private fun resumePlayer(){
        novellaProgress.resume()
        storyPlayer.play()
    }

    override fun onNext(position:Int) {
        if (MediaType.VIDEO == listOfVideos[position].url.checkMediaType()){
            playVideo(position)
        }else{
            storyPlayer.pause()
            showImage(position)
        }
    }

    override fun onPrevious(position: Int) {
        if (MediaType.VIDEO == listOfVideos[position].url.checkMediaType()){
            playVideo(position)
        }else{
            storyPlayer.pause()
            showImage(position)
        }

    }

    override fun onEnd() {
//        storyPlayer.stop()
        //implement: goto next user
    }

    override fun onPauseOrResume(isPlaying: Boolean, position: Int) {
        if(isPlaying && MediaType.VIDEO == listOfVideos[position].url.checkMediaType()){
                storyPlayer.play()
        }else if(!isPlaying && MediaType.VIDEO == listOfVideos[position].url.checkMediaType()){
                storyPlayer.pause()
        }
    }

    override fun onProgressStarts() {
//        checkIsVideoOrImage(0)
        if(MediaType.IMAGE == listOfVideos[0].url.checkMediaType()){
            showImage(0)
        }else{
            playVideo(0)
        }
    }


    private val playerEventListener = object : Player.EventListener{
        override fun onPlaybackStateChanged(state: Int) {
            super.onPlaybackStateChanged(state)
            Log.d(TAG,"======== player state ==== $state")
            if(state == Player.STATE_BUFFERING){
                novellaProgress.pause()
            }else if(state == Player.STATE_READY){
                novellaProgress.resume()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        novellaProgress.destroy()
    }

}
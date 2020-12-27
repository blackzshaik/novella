package com.blackz.novella.customviews

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.viewpager2.widget.ViewPager2
import com.blackz.novella.R
import com.blackz.novella.listeners.ProgressListener
import com.blackz.novella.util.listOfVideos

class NovellaProgress @JvmOverloads
constructor (context: Context, attributeSet: AttributeSet? = null, defStyleAttr:Int =0 ) :
    LinearLayout(context,attributeSet,defStyleAttr){


    private var count: Int = 0
    private var progressListener: ProgressListener? = null
    private val TAG = this::class.simpleName
    private lateinit var storyPager: ViewPager2
    private var currentlyPlayingAnimationFor = 0
    private lateinit var currentAnimation:ObjectAnimator
    private var isPrevious = false

    private val progressBarLayoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f).apply {
        marginStart = 2
        marginEnd = 2
    }


    init{
        orientation = LinearLayout.HORIZONTAL;
    }

    fun setupView(count: Int){
        removeAllViews()
        this.count = count - 1
        val width = findViewById<LinearLayout>(R.id.novellaProgress).width
        Log.d(TAG,"========== $width")
        val minHeight = (width / count )
        for(i in 0..this.count){
            Log.d(TAG,"====== $i")
            val customProgresBar = CustomProgressBar(ContextThemeWrapper(context,R.style.Widget_AppCompat_ProgressBar_Horizontal))
            customProgresBar.layoutParams = progressBarLayoutParams
            addView(customProgresBar)

        }
        startAnimation()
    }

    fun pause(){
        currentAnimation.pause()
        progressListener?.onPauseOrResume(false)
    }


    fun resume(){
        currentAnimation.resume()
        progressListener?.onPauseOrResume(true)
    }

    fun next(){
        currentAnimation.end()
    }

    fun previous(){

        isPrevious = true
        currentAnimation.end()
    }

    private fun startAnimation(index :Int = 0,from:String = "M"){
        Log.d(TAG,"index =========== $index from ===== $from")

        currentlyPlayingAnimationFor = index
        val rView = getChildAt(index) as ProgressBar
        currentAnimation = ObjectAnimator.ofInt(rView,"progress",0,100).apply {
            duration = 5000
            start()
            addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    if(!isPrevious){
                        if(hasNext()){
                            startAnimation(index + 1,"A")
                            progressListener?.onNext()
                        }else{
                            Log.d(TAG,"Finished")
                            progressListener?.onEnd()
                        }
                    }else{
                        isPrevious = false
                        if(hasPrevious()){
                            (currentAnimation.target as ProgressBar).progress = 0
                            startAnimation(index - 1 )
                            progressListener?.onPrevious()
                        }else{
                            startAnimation(0,"C")
                        }
                    }

                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {}

            })
        }
    }

    private fun hasNext():Boolean {
        return currentlyPlayingAnimationFor  != count
    }

    private fun hasPrevious():Boolean {
        return currentlyPlayingAnimationFor - 1 != -1
    }

    fun startFrom(index: Int) {
        startAnimation(index)
    }

    fun setupProgressListener(progressListener: ProgressListener) {
        this.progressListener = progressListener
    }

    fun destroy(){
        removeAllViews()
        progressListener = null
        currentAnimation.removeAllListeners()
    }


}
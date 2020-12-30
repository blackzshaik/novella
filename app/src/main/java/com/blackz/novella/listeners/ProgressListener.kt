package com.blackz.novella.listeners

interface ProgressListener {

    fun onNext(position:Int)
    fun onPrevious(position: Int)
    fun onEnd()
    fun onPauseOrResume(isPlaying:Boolean,position: Int)
    fun onProgressStarts()
}
package com.blackz.novella.listeners

interface ProgressListener {

    fun onNext()
    fun onPrevious()
    fun onEnd()
    fun onPauseOrResume(isPlaying:Boolean)
}
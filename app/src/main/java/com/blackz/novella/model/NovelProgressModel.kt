package com.blackz.novella.model

data class NovelProgressModel(val content:String = "",val type:MediaType)

enum class MediaType{
    IMAGE, //for valid image files
    VIDEO, // for valid video files
    TEXT, // for only text to show in textview
    UNKNOWN // for files other than images and videos
}
package com.blackz.novella.model

data class NovelProgressModel(val url:String = "",val type:MediaType)

enum class MediaType{
    IMAGE, //for valid image files
    VIDEO, // for valid video files
    UNKNOWN // for files other than images and videos
}
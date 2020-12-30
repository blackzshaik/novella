package com.blackz.novella.util

import com.blackz.novella.model.MediaType
import com.blackz.novella.model.NovelProgressModel

val listOfVideos = listOf<NovelProgressModel>(
    NovelProgressModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        MediaType.VIDEO
    ),
    NovelProgressModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",MediaType.IMAGE),
    NovelProgressModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
        MediaType.VIDEO
    ),
    NovelProgressModel(    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
        MediaType.VIDEO
    ),
    NovelProgressModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",MediaType.IMAGE),
    NovelProgressModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg",MediaType.IMAGE),
    NovelProgressModel(    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
        MediaType.VIDEO
    ),
    NovelProgressModel(    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
        MediaType.VIDEO
    ),
    NovelProgressModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",MediaType.IMAGE),
    NovelProgressModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",MediaType.IMAGE)
    )

//glide supported image extension
//these are commonly used formats , item can be added if other than these formats supported and required
val imageExtension = listOf<String>(".jpg",".jpeg",".png",".gif",".bmp")

//commonly used video formats, item can be added according to requirement
val videoExtenstion = listOf(".mp4",".mkv",".webm",".m3u8"/*not so sure about this one*/)

// "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
//"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
//"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
//"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
package com.blackz.novella.util

import android.view.View
import com.blackz.novella.model.MediaType

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}


/**
 * Not necessary sample data provided in this code,
 * but could be use full if we don't know the type
 */
fun String.checkMediaType(): MediaType {
    return when {
        this.endsWithAnyOf(imageExtension) -> {
            MediaType.IMAGE
        }
        this.endsWithAnyOf(videoExtenstion) -> {
            MediaType.VIDEO
        }
        else -> {
            //todo; unhandled case
            MediaType.UNKNOWN
        }
    }
}

private fun String.endsWithAnyOf(list:List<String>):Boolean{
    for (l in list){
        if(this.endsWith(l,true)){
            return true
        }
    }
    return false
}
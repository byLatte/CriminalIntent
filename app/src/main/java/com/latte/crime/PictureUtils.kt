package com.latte.crime

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point

fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
    // 파일크기 읽기
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)

    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()

    var inSampleSize = 1
    if(srcWidth > destWidth || srcHeight > destHeight){
        val widthScale = srcWidth / destWidth
        val heightScale = srcHeight / destHeight

        val sampleScale = if(heightScale > widthScale){
            heightScale
        }else{
            widthScale
        }
        inSampleSize = Math.round(sampleScale)
    }
    options = BitmapFactory.Options()
    options.inSampleSize = inSampleSize

    return BitmapFactory.decodeFile(path, options)

}

fun getScaledBitmap(path: String, activity: Activity): Bitmap{
    val size = Point()

    @Suppress("DEPRECATION")
    activity.windowManager.defaultDisplay.getSize(size)

    return getScaledBitmap(path, size.x, size.y)
}

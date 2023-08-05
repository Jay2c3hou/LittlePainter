package com.example.littlepainter.utils

import android.graphics.Color
import android.util.Log
import kotlin.random.Random

object ColorUtils {
    fun getRandomColor(count:Int):List<Int>{
        val colorList = arrayListOf<Int>()

        for (i in 0..count) {
            val red = Random.nextInt(255)
            val green =  Random.nextInt(255)
            val blue =  Random.nextInt(255)
            val color = Color.rgb(red, green, blue)
            Log.v("pxd","color: $red $green $blue")
            colorList.add(color)
        }

        return colorList
    }
}
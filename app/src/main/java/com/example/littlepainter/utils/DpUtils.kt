package com.example.littlepainter.utils

import android.content.Context
import android.view.View

fun View.dp2px(dp:Int):Int{
    return (context.resources.displayMetrics.density*dp).toInt()
}

fun Context.dp2px(dp:Int):Int{
    return (resources.displayMetrics.density*dp).toInt()
}
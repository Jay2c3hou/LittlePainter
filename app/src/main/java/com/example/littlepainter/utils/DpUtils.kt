package com.example.littlepainter.utils

import android.view.View

fun View.dp2px(dp: Int) = (context.resources.displayMetrics.density * dp).toInt()
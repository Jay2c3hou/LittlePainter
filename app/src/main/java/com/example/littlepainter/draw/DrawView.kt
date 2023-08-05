package com.example.littlepainter.draw

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner

class DrawView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var viewModel:DrawViewModel? = null
    var lifeCycleOwner:LifecycleOwner? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (viewModel != null && lifeCycleOwner != null){
            viewModel!!.mToolType.observe(lifeCycleOwner!!){
            }
            viewModel!!.mLineWidth.observe(lifeCycleOwner!!){

            }
            viewModel!!.mPaintColor.observe(lifeCycleOwner!!){

            }
        }
    }

}
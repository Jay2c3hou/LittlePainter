package com.example.littlepainter.draw

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.LifecycleOwner

class DrawView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var viewModel:DrawViewModel? = null
    var lifeCycleOwner:LifecycleOwner? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (viewModel != null && lifeCycleOwner != null){
            //监听工具类型
            viewModel!!.mToolType.observe(lifeCycleOwner!!){

            }
            //监听线条粗细
            viewModel!!.mLineWidth.observe(lifeCycleOwner!!){

            }
            //监听画笔颜色
            viewModel!!.mPaintColor.observe(lifeCycleOwner!!){

            }
        }
    }

}
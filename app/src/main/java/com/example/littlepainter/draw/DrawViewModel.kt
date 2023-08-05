package com.example.littlepainter.draw

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.littlepainter.utils.dp2px

class DrawViewModel(application: Application):AndroidViewModel(application) {
    //工具类型
    var mToolType = MutableLiveData(ToolType.None)
    //线条粗细
    var mLineWidth = MutableLiveData(application.dp2px(5))
    //画笔颜色
    var mPaintColor = MutableLiveData(Color.BLACK)

    fun setType(type:ToolType){
        mToolType.postValue(type)
    }
    fun setLineWidth(size:Int){
        mLineWidth.postValue(size)
    }
    fun setColor(color:Int){
        mPaintColor.postValue(color)
    }
}
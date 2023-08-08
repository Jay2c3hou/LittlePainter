package com.example.littlepainter.draw

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.littlepainter.draw.color.ColorModel
import com.example.littlepainter.utils.ColorUtils
import com.example.littlepainter.utils.dp2px

class DrawViewModel(application: Application):AndroidViewModel(application) {
    //工具类型
    val mToolType = MutableLiveData(ToolType.None)
    //线条粗细
    val mLineWidth = MutableLiveData(application.dp2px(1))
    //画笔颜色
    val mPaintColor = MutableLiveData(Color.WHITE)
    //颜色模型
    val mColorModels  = MutableLiveData(emptyList<ColorModel>())

    init {
        loadColors(100)
    }

    fun setType(type:ToolType){
        mToolType.postValue(type)
    }
    fun setLineWidth(size:Int){
        mLineWidth.postValue(getApplication<Application>().dp2px(size))
    }
    fun setColor(color:Int){
        if (color == -1) {
            mPaintColor.postValue(Color.WHITE)
        }else {
            mPaintColor.postValue(color)
        }
    }

    //创建颜色的模型对象
    fun loadColors(count:Int){
        val models = arrayListOf<ColorModel>()
        ColorUtils.getRandomColor(count).forEach { color ->
            models.add(ColorModel(color))
        }
        mColorModels.postValue(models)
    }
}
package com.example.littlepainter.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.littlepainter.R

//setValue(value: T)：在主线程中更新 LiveData 的值。
//postValue(value: T)：在非主线程中更新 LiveData 的值。
class PictureViewModel(application: Application) : AndroidViewModel(application) {
    val models = MutableLiveData<List<Picture>>(emptyList())

    /** 现在哥们要写加载假数据*/
    fun loadData() {
        val datas = listOf(
            Picture(0, ",", "", R.drawable.f1),
            Picture(0, ",", "", R.drawable.f2),
            Picture(0, ",", "", R.drawable.f3),
            Picture(0, ",", "", R.drawable.f4),
            Picture(0, ",", "", R.drawable.f1),
            Picture(0, ",", "", R.drawable.f2),
            Picture(0, ",", "", R.drawable.f3),
            Picture(0, ",", "", R.drawable.f4)
        )
//在主线程中更新 LiveData 的值。
        models.postValue(datas)
    }
}
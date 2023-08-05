package com.example.littlepainter.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class ScaleLayoutManager(
    context: Context,
    orientation: Int = RecyclerView.HORIZONTAL,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    //子控件布局完毕
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        //完成缩放功能
        scaleChild()
    }

    //横向滚动
    override fun scrollHorizontallyBy(
        dx: Int, //横向滚动的距离
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State? //start layout animation
    ): Int {
        val distance = super.scrollHorizontallyBy(dx, recycler, state)
        //完成缩放
        scaleChild()
        return distance
    }

    fun scaleChild() {
        //当前的中心点
        val center = width / 2f
        //遍历子控件
        for (i in 0 until childCount) {
            val child = getChildAt(i)!!
            //计算子控件的中心点
            val childCenter = (child.left + child.right) / 2f
            //算出当前的中心点和子控件的距离
            val distance = abs(center - childCenter)
            //计算出缩放比例
            val scale = 1 - distance / center * 0.1f
            //对子控件进行缩放
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}
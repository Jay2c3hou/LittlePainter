package com.example.littlepainter.bottomView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.children
import androidx.navigation.NavController
import com.example.littlepainter.R
import com.google.android.material.internal.ViewUtils.dpToPx
import java.lang.ref.WeakReference

/**
 * View:
 *   onMeasure ——> 宽高由父容器 或者 自己决定
 *   onDraw
 *
 * ViewGroup
 *   onMeasure ——> 由父容器  自己  子控件决定
 *   onLayout ——> 布局每一个子控件
 *
 *   childCount 子控件个数
 *   getChildAt 获取某个子控件
 */
class BottomView(context:Context, attrs:AttributeSet?): ViewGroup(context, attrs) {
    private var mSize = 0
    private lateinit var childList:List<TabItem>
    private var currentSelectedIndex = 0 //当前选中的tab的索引
    private lateinit var lastSelectedTab:TabItem //上一个
    private lateinit var  weakNavController: WeakReference<NavController>
    private lateinit var mConfigures:List<Int>  //fragment id
//    init {
//        setBackgroundColor(resources.getColor(R.color.dark,null))
//    }

    @SuppressLint("RestrictedApi")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mWidth = MeasureSpec.getSize(widthMeasureSpec)
        val mHeight = dpToPx(context,64).toInt()
        setMeasuredDimension(mWidth,mHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mSize = w/4
        childList = children.toList() as List<TabItem>
        //给每个TabItem编号
        childList.forEachIndexed { index, tabItem ->
            tabItem.index = index
            tabItem.fragmentId = mConfigures[index]
            tabItem.addListener = { tabItem, i ->   //监听点击回调事件
                selectTabItem(i)
            }
        }
        //默认选中0
        val item0 = childList[currentSelectedIndex]
        item0.select()
        lastSelectedTab = item0

        weakNavController.get()?.let {
            it.popBackStack()
            it.navigate(item0.fragmentId)
        }
    }

    private fun selectTabItem(index:Int){
        //判断是否已经时选中的
        val tabItem = childList[index]
        if (tabItem.index == lastSelectedTab.index) return

        //取消上一个的状态
        lastSelectedTab.deSelected()

        //选中当前这个
        tabItem.select()
        lastSelectedTab = tabItem

        weakNavController.get()?.let {
            it.popBackStack()
            it.navigate(tabItem.fragmentId)
        }
    }

    fun setupWithNavController(navController: NavController, configure:List<Int>){
        weakNavController = WeakReference(navController)
        mConfigures = configure
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount){
            //获取i对应的子控件
            val mChild = getChildAt(i)
            //布局这个子控件
            val left = i*mSize
            mChild.layout(left,0,left+mSize,height)
        }
    }
}
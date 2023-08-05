package com.example.littlepainter.draw

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children

/**管理工具类*/
class ToolContainerView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private var mLastSelectView:ToolView? = null //上一次选中的控件
    var viewModel:DrawViewModel? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        //给所有的子控件添加点击回调事件
        children.forEach { it ->
            if (it !is ImageView) {
                val child = it as ToolView
                child.addToolClickListener = { view, toolType ->
                    if (toolType == ToolType.None) {
                        mLastSelectView = null
                    } else {
                        mLastSelectView?.changeSelectState(false)
                        mLastSelectView = view
                    }
                    viewModel?.let { it.setType(toolType) }
                }
            }
        }
    }
}
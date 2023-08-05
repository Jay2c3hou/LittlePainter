package com.example.littlepainter.draw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import com.example.littlepainter.R
import com.example.littlepainter.utils.dp2px

/***
 * 自定义属性？ 需不需要在xml中配置这个View
 *      icon
 */
@SuppressLint("UseCompatLoadingForDrawables")
class ToolView(context:Context, attrs:AttributeSet?) : ViewGroup(context, attrs){
    private lateinit var mIcon:Drawable
    private lateinit var mBackgroundView:ImageView
    private lateinit var mIconView:ImageView
    private lateinit var mBgIcon:Drawable
    private  var mIconSize = 0  //图标的尺寸
    private lateinit var mType:ToolType
    private var mSelected = false //默认没选中

    var addToolClickListener:((ToolView,ToolType)->Unit)? = null //将工具被点击的事件传递出去

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ToolView).apply {
            mIcon = getDrawable(R.styleable.ToolView_icon) ?: resources.getDrawable(R.drawable.logo2,null)
            mBgIcon = getDrawable(R.styleable.ToolView_bg_icon) ?: resources.getDrawable(R.drawable.round_bg,null)
            mIconSize = getDimension(R.styleable.ToolView_icon_size, dp2px(16).toFloat()).toInt()

            //获取配置的工具类型
            val enumValue =    getInteger(R.styleable.ToolView_tool_type, ToolType.Move.value)
            mType = ToolType.values()[enumValue-1]

            recycle()

            //添加控件
            addSubviews()
        }

        //添加点击事件
        setOnClickListener {
            var type = mType
            if (mSelected) {//选中 需要取消
                changeSelectState(false)
                type = ToolType.None
            }else{ //需要选中
                changeSelectState(true)
            }
            addToolClickListener?.let { it(this,type) }
        }
    }

    fun changeSelectState(isSelect:Boolean){
        if (isSelect == mSelected) return
        if (isSelect) {
            mBackgroundView.setImageResource(R.drawable.round_bg_select)
        }else{
            mBackgroundView.setImageDrawable(mBgIcon)
        }
        mSelected = isSelect
    }

    private fun addSubviews(){
        //添加背景
        mBackgroundView = ImageView(context).apply {
            setImageDrawable(mBgIcon)
            addView(this, LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT))
        }
        mIconView = ImageView(context).apply {
            setImageDrawable(mIcon)
            addView(this,mIconSize,mIconSize)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mWidth = MeasureSpec.getSize(widthMeasureSpec)
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY){
            mWidth = dp2px(40)
        }
        var mHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY){
            mHeight = dp2px(40)
        }
        setMeasuredDimension(mWidth,mHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //布局背景视图
        mBackgroundView.layout(0,0,measuredWidth,measuredHeight)
        val left = (width - mIconSize)/2
        val top = (height - mIconSize)/2
        mIconView.layout(left,top,left+mIconSize,top+mIconSize)
    }
}
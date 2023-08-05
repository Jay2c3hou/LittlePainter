package com.example.littlepainter.bottomView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import com.example.littlepainter.R
import com.google.android.material.internal.ViewUtils.dpToPx

class TabItem(context: Context, attrs: AttributeSet?): ViewGroup(context, attrs)  {
    private lateinit var mTitle:String
    private lateinit var mIcon:Drawable
    private lateinit var mSelectIcon:Drawable
    private var mColor:Int = 0
    private var mSelectColor:Int = 0

    private lateinit var mTabIcon:ImageView
    private lateinit var mTabTitle:TextView

    @SuppressLint("RestrictedApi")
    private var mIconSize = dpToPx(context,24).toInt()  //图标的大小
    private var mTextSize = 12f
    @SuppressLint("RestrictedApi")
    private var mSpace = dpToPx(context,3).toInt()

    var index = 0 //当前item的编号
    var addListener:((TabItem,Int)->Unit)? = null //回调

    var fragmentId = 0 //导航的页面

    init {
        parseAttrs(attrs)
        addChild()
        initEvent()
    }

    private fun initEvent(){
        //添加点击事件
        setOnClickListener {
            //加载动画资源
            val scaleAnim = AnimationUtils.loadAnimation(context,R.anim.tab_item_select_anim).apply {
                interpolator = BounceInterpolator()
            }
            mTabIcon.startAnimation(scaleAnim)
            mTabTitle.startAnimation(scaleAnim)

            //事件回调
            addListener?.let { it(this,index) }
        }
    }

    /**取消选中*/
    fun deSelected(){
        changeSelectState(false)
    }

    fun select(){
        changeSelectState(true)
    }

    /**更改对应状态显示的颜色*/
    private fun changeSelectState(state:Boolean){
        val color = if (state) mSelectColor else mColor
        //设置选中图片
        mTabIcon.setImageDrawable(getColoredDrawable(mIcon,color))
        //更改文字的颜色
        mTabTitle.setTextColor(color)
    }

    /**给drawable渲染颜色*/
    private fun getColoredDrawable(drawable: Drawable, color:Int):Drawable{
        val compatDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(compatDrawable, color)
        return compatDrawable
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun parseAttrs(attrs: AttributeSet?){
        context.obtainStyledAttributes(attrs, R.styleable.TabItem).apply {
            mTitle = getString(R.styleable.TabItem_title) ?: "Tab"

            mIcon =  getDrawable(R.styleable.TabItem_micon)?: resources.getDrawable(R.drawable.ic_launcher_foreground,null)
            mSelectIcon = getDrawable(R.styleable.TabItem_select_icon)?: resources.getDrawable(R.drawable.ic_launcher_foreground,null)
            mColor = getColor(R.styleable.TabItem_normal_color, Color.BLACK)
            mSelectColor = getColor(R.styleable.TabItem_select_color, mColor)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
    }
    private fun addChild(){
        //图标
        mTabIcon = ImageView(context).apply {
            setImageDrawable(mIcon)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        //标题
        mTabTitle = TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP,mTextSize)
            setTextColor(mColor)
            text = mTitle
        }
        addView(mTabIcon, mIconSize,mIconSize) //尺寸固定
        addView(mTabTitle,LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT))//内容决定

        measureChild(mTabTitle,MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
    }

    /***
     * 在viewGroup中添加子控件（自己的还是系统的）
     * 如果没有明确指定控件的尺寸时，但有需要获取控件的尺寸
     * 必须先测量这个控件本身，测量完毕之后使用measureWidth 和 measureHeight获取对应的宽高
     * 只有在onLayout结束之后，才能使用width和height获取子控件的宽高
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val iconLeft = (width - mIconSize)/2
        val iconTop = (height - mIconSize - mTabTitle.measuredHeight - mSpace)/2
        mTabIcon.layout(iconLeft,iconTop,iconLeft+mIconSize,iconTop+mIconSize)

        val titleLeft = (width - mTabTitle.measuredWidth)/2
        val titleTop = height - iconTop - mTabTitle.measuredHeight
        mTabTitle.layout(titleLeft,titleTop,titleLeft+mTabTitle.measuredWidth,titleTop+mTabTitle.measuredHeight)
    }
}
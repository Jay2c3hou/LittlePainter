package com.example.littlepainter.guide

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.littlepainter.R
import com.example.littlepainter.utils.dp2px

class IndicatorView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var mSelectedWidth = dp2px(40)//选中的点的宽度
    private var mNormalWidth = dp2px(20)//默认宽度
    private var mHeight = dp2px(12)//点的高度
    private var mCornerRadius = dp2px(6).toFloat()//圆角半径
    private var mSpace = dp2px(6)//间距
    private var mCurrentIndex = 2 //默认选中第一个点
    private var mSelectedColor = resources.getColor(R.color.main_red, null)
    private var mNormalColor = resources.getColor(R.color.light_dark)//没写null

    private val mPaint = Paint().apply {
        color = mNormalColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var m_width = MeasureSpec.getSize(widthMeasureSpec)
        val width_mode = MeasureSpec.getMode(widthMeasureSpec)
        if (width_mode != MeasureSpec.EXACTLY) {//需要自己去计算
            m_width = 3 * mNormalWidth + mSelectedWidth + 3 * mSpace
        }
        var m_height = MeasureSpec.getSize(heightMeasureSpec)
        val height_mode = MeasureSpec.getMode(heightMeasureSpec)
        if (height_mode != MeasureSpec.EXACTLY) {//需要自己去计算
            m_height = mHeight
        }
        //确定好了,告诉父容器
        setMeasuredDimension(m_width, m_height)
    }

    private var mStartX: Float = 0f
    private var mTop = 0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mStartX = (width - 3 * mNormalWidth - mSelectedWidth - 3 * mSpace).toFloat() / 2
        mTop = (height - mHeight).toFloat() / 2
    }

    //提供给外部设置当前选中哪一个
    fun select(index: Int) {
        if (mCurrentIndex == index) return
        mCurrentIndex = index
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until 4) {
            val left = mStartX + if (i > mCurrentIndex) {
                mSelectedWidth + mSpace + (i - 1) * (mNormalWidth + mSpace)
            } else {
                i * (mNormalWidth + mSpace)
            }
            val right = left + if (i == mCurrentIndex) mSelectedWidth else mNormalWidth
            mPaint.color = if (i == mCurrentIndex) mSelectedColor else mNormalColor
            canvas?.drawRoundRect(
                left,
                mTop,
                right,
                mTop + mHeight,
                mCornerRadius,
                mCornerRadius,
                mPaint
            )
        }
    }

    var addPagerChangeCallBack: ((page: Int) -> Unit)? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            //判断点击的左边还是右边
            if (event.x > width / 2) {
                //右边
                mCurrentIndex++
                if (mCurrentIndex == 4) {
                    mCurrentIndex-- //3
                }
            } else {
                //左边
                mCurrentIndex--
                if (mCurrentIndex == -1) {
                    mCurrentIndex = 0
                }
            }
            addPagerChangeCallBack?.let {
                it(mCurrentIndex) //回调出去
            }
            invalidate()
        }

        return true
    }
}
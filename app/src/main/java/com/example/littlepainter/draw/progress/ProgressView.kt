package com.example.littlepainter.draw.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.littlepainter.R
import com.example.littlepainter.draw.DrawViewModel
import com.example.littlepainter.utils.dp2px
import com.example.littlepainter.utils.sp2px

class ProgressView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var mDrawViewModel: DrawViewModel? = null
    private var mMinValue = 0
    private var mMaxValue = 0
    var currentProgress = 0
    private var mDefaultWidth = dp2px(100)
    private var mDefaultHeight = dp2px(10)
    private var mCircleRadius = dp2px(10)
    private var mCornerRadius = mDefaultHeight / 2
    private val mBackgroundColor = resources.getColor(R.color.light_dark, null)
    private val mForegroundColor = resources.getColor(R.color.main_red, null)
    private val mTextColor = Color.parseColor("#E5E5E5")
    private val mTextSize = context.sp2px(10)
    private val mTextPaint = TextPaint().apply {
        color = mTextColor
        textSize = mTextSize
    }
    private var mProgressPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ProgressView).apply {
            mMinValue = getInteger(R.styleable.ProgressView_min, 1)
            mMaxValue = getInteger(R.styleable.ProgressView_max, 50)
            currentProgress = getInteger(R.styleable.ProgressView_current_progress, 1)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mWidth = MeasureSpec.getSize(widthMeasureSpec)
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
            mWidth = mDefaultWidth
        }
        var mHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            mHeight = mCircleRadius * 2
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCornerRadius = height / 2
        mCircleRadius = height / 2
        mDefaultWidth = width

        mDrawViewModel?.let {
            it.setLineWidth(currentProgress)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制背景线条
        mProgressPaint.color = mBackgroundColor
        canvas?.drawRoundRect(
            0f,
            (height - mDefaultHeight).toFloat() / 2,
            mDefaultWidth.toFloat(),
            (height + mDefaultHeight).toFloat() / 2,
            mCornerRadius.toFloat(),
            mCornerRadius.toFloat(),
            mProgressPaint
        )

        //绘制进度条
        mProgressPaint.color = mForegroundColor
        canvas?.drawRoundRect(
            0f,
            (height - mDefaultHeight).toFloat() / 2,
            currentProgress.toFloat() / mMaxValue * mDefaultWidth,
            (height + mDefaultHeight).toFloat() / 2,
            mCornerRadius.toFloat(),
            mCornerRadius.toFloat(),
            mProgressPaint
        )

        //绘制圆点
        var cx = currentProgress.toFloat() / mMaxValue * mDefaultWidth
        if (cx < mCircleRadius) { //不少于左边  cx = mCircleRadius
            cx = mCircleRadius.toFloat()
        }
        if (cx + mCircleRadius > width) {//不超过最右边
            cx = width - mCircleRadius.toFloat()
        }
        mProgressPaint.color = mForegroundColor
        canvas?.drawCircle(
            cx,
            height.toFloat() / 2,
            mCircleRadius.toFloat(),
            mProgressPaint
        )

        //绘制文字
        val text = "$currentProgress"
        val textWidth = mTextPaint.measureText(text)
        val tx = cx - textWidth / 2
        var ty = 0f
        mTextPaint.fontMetrics.apply {
            ty = (height - this.top - this.bottom) / 2
        }
        canvas?.drawText(text, tx, ty, mTextPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                changeProgress(event.x)
            }

            MotionEvent.ACTION_MOVE -> {
                changeProgress(event.x)
            }

            MotionEvent.ACTION_UP -> {
                mDrawViewModel?.let { viewModel ->
                    viewModel.setLineWidth(currentProgress)
                }
            }
        }
        return true
    }

    private fun changeProgress(tx: Float) {
        if (tx <= 0 || tx > width) return
        currentProgress = (tx / width * mMaxValue).toInt()
        if (currentProgress == 0) {
            currentProgress = 1
        }
        invalidate()
    }
}

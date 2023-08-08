package com.example.littlepainter.draw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.littlepainter.draw.shape.RectangleShape
import com.example.littlepainter.draw.shape.Shape
import com.example.littlepainter.draw.shape.StraightLineShape

/**
 * 1. 和外界交互 设置粗细 颜色 工具类型
 * 2. 管理自己的内容（各种形状）
 * 3. 接收触摸事件
 */
class DrawView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var viewModel:DrawViewModel? = null
    var lifeCycleOwner:LifecycleOwner? = null
    private val mShapeList = arrayListOf<Shape>() //保存所有的形状
    private var mToolType = ToolType.None
    private var mLineWidth = 0
    private var mStrokeColor = 0
    private var mCurrentShape:Shape? = null  //当前正在操作的形状

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (viewModel != null && lifeCycleOwner != null){
            //监听工具类型
            viewModel!!.mToolType.observe(lifeCycleOwner!!){
                mToolType = it
            }
            //监听线条粗细
            viewModel!!.mLineWidth.observe(lifeCycleOwner!!){
                mLineWidth = it
            }
            //监听画笔颜色
            viewModel!!.mPaintColor.observe(lifeCycleOwner!!){
                mStrokeColor = it
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            mShapeList.forEach { shape ->
                shape.drawShape(canvas)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                //创建形状
                addShape(event.x,event.y)
            }
            MotionEvent.ACTION_MOVE ->{
                //改变这个形状的位置 大小
                changeShape(event.x,event.y)
            }
            MotionEvent.ACTION_UP ->{

            }
        }
        return true
    }

    private fun addShape(tx:Float, ty:Float){
        when(mToolType){
            ToolType.None -> {}
            ToolType.Line ->{
                StraightLineShape().also {
                    it.strokeColor = mStrokeColor
                    it.strokeWidth = mLineWidth
                    it.startPoint = PointF(tx,ty)
                    mShapeList.add(it)
                    mCurrentShape = it
                }
            }
            ToolType.Rectangle ->{
                RectangleShape().also {
                    it.strokeColor = mStrokeColor
                    it.strokeWidth = mLineWidth
                    it.startPoint = PointF(tx,ty)
                    mShapeList.add(it)
                    mCurrentShape = it
                }
            }
            else -> {}
        }
    }
    private fun changeShape(tx:Float, ty:Float){
        mCurrentShape?.endPoint = PointF(tx,ty)
        invalidate()
    }
}

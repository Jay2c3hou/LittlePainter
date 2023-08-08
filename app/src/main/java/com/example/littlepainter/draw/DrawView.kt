package com.example.littlepainter.draw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.littlepainter.R
import com.example.littlepainter.draw.shape.BezelShape
import com.example.littlepainter.draw.shape.CircleShape
import com.example.littlepainter.draw.shape.CurveShape
import com.example.littlepainter.draw.shape.RectangleShape
import com.example.littlepainter.draw.shape.Shape
import com.example.littlepainter.draw.shape.StraightLineShape
import com.example.littlepainter.draw.shape.TriangleShape

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
    private var mOperationType = OperationType.None //记录用户对图形的操作类型
    private var mTouchX = 0f  //移动 旋转 缩放时 的起始触摸点
    private var mTouchY = 0f

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
                if (mToolType == ToolType.Delete){ //清空
                    mShapeList.clear()
                    mCurrentShape = null
                    invalidate()
                }
                if (mToolType == ToolType.Back){//撤销
                    if (mShapeList.size > 0){
                        mShapeList.removeLast()
                        mCurrentShape = null
                        invalidate()
                    }
                }
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
                shape.draw(canvas)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                //创建形状
                addShape(event.x,event.y)
                mTouchX = event.x
                mTouchY = event.y
            }
            MotionEvent.ACTION_MOVE ->{
                when(mOperationType){
                    OperationType.None -> changeShape(event.x,event.y) //改变这个形状的位置 大小
                    OperationType.Translate -> {
                        mCurrentShape?.move(event.x-mTouchX, event.y-mTouchY) //移动这个图形
                        invalidate()
                        mTouchX = event.x
                        mTouchY = event.y
                    }
                    else ->{}
                }

            }
            MotionEvent.ACTION_UP ->{
                mCurrentShape = null
                mOperationType = OperationType.None
            }
        }
        return true
    }

    private fun addShape(tx:Float, ty:Float){
        when(mToolType){
            ToolType.None -> { mCurrentShape = null}
            ToolType.Line ->{
                StraightLineShape().also { initShape(it, tx, ty) }
            }
            ToolType.Rectangle ->{
                RectangleShape().also { initShape(it, tx, ty) }
            }
            ToolType.Circle ->{
                CircleShape().also { initShape(it, tx, ty) }
            }
            ToolType.Triangle ->{
                TriangleShape().also { initShape(it, tx, ty) }
            }
            ToolType.Curve ->{
                CurveShape().also { initShape(it, tx, ty) }
            }
            ToolType.Bezel->{
                BezelShape().also { initShape(it, tx, ty) }
            }
            ToolType.Eraser->{
                CurveShape().also {
                    initShape(it, tx, ty)
                    it.strokeColor = resources.getColor(R.color.background_dark,null)
                }
            }
            ToolType.Move->{
                mOperationType = OperationType.Translate
                //查找触摸点在按个图形内部
                mCurrentShape = shapeContainsPoint(tx,ty)
//                mCurrentShape?.let {
//                    mShapeList.remove(mCurrentShape)
//                    mShapeList.add(mCurrentShape!!)
//                }

            }
            ToolType.Brush->{
                //查找触摸点在按个图形内部
                mCurrentShape = shapeContainsPoint(tx,ty)
                mCurrentShape?.let {
                    it.fillColor(mStrokeColor)
                    invalidate()
//                    mShapeList.remove(mCurrentShape)
//                    mShapeList.add(mCurrentShape!!)
                    mCurrentShape = null
                }
            }
            else -> {}
        }
    }

    /**查找某个触摸点是否在一个图形内部 找到就返回这个图形 否则返回null*/
    private fun shapeContainsPoint(x:Float, y:Float):Shape?{
        for (i in mShapeList.size-1 downTo 0 ){
            val shape = mShapeList[i]
            if(shape.containsPoint(x,y)){
                return shape
            }
        }
        return null
    }

    private fun initShape(shape:Shape, tx:Float, ty:Float){
        shape.strokeColor = mStrokeColor
        shape.strokeWidth = mLineWidth
        shape.startPoint = PointF(tx,ty)
        mShapeList.add(shape)
        mCurrentShape = shape
    }

    private fun changeShape(tx:Float, ty:Float){
        mCurrentShape?.endPoint = PointF(tx,ty)
        invalidate()
    }
}

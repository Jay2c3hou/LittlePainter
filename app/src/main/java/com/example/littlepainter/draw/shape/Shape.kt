package com.example.littlepainter.draw.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

abstract class Shape {
    var strokeColor: Int = 0
    var strokeWidth: Int = 0
    open var startPoint = PointF(0f, 0f)
    var endPoint = PointF(0f, 0f)
    val mPaint: Paint by lazy {
        Paint().also {
            it.style = Paint.Style.STROKE
            it.color = strokeColor
            it.strokeWidth = strokeWidth.toFloat()
            it.isAntiAlias = true
        }
    }

    fun move(x: Float, y: Float) {
        mTranslateX = x
        mTranslateY = y
    }

    abstract fun containsPoint(x: Float, y: Float): Boolean
    abstract fun drawShape(canvas: Canvas)

    fun fillColor(color: Int) {
        mPaint.style = Paint.Style.FILL
        mPaint.color = color
    }

    fun draw(canvas: Canvas) {
        //把当前已经绘制好的内容独立保存起来
        //创建一个新的图层
        canvas.save()
        if (mTranslateX != 0f || mTranslateY != 0f) {
            //将图层移动
//        canvas.translate() 方法用于将坐标系平移指定的偏移量 (mTranslateX, mTranslateY)
            canvas.translate(mTranslateX, mTranslateY)
            //canvas.scale()
            //canvas.rotate()
        }
        //图层操作完毕之后再绘制自己的内容
        drawShape(canvas)
        //画完之后 改变起始点终点 缩放平移 旋转的值
        initData()
        //合并图层
        canvas.restore()
    }

    var mTranslateX = 0f
    var mTranslateY = 0f

    private fun initData() {
        startPoint.x = startPoint.x + mTranslateX
        startPoint.y = startPoint.y + mTranslateY
        endPoint.x = endPoint.x + mTranslateX
        endPoint.y = endPoint.y + mTranslateY
        mTranslateX = 0f
        mTranslateY = 0f
    }
}

/***
 * Shape
 *      color
 *      strokeWidth
 *      startX
 *      endY
 *      scale
 *      translateX
 *      translateY
 *      rotation
 *      fun drawShape()
 *
 *  CurveShape //没有形状 自由画
 *      fun drawShape(){}
 *  RectangleShape
 *      fun drawShape(){}
 *  TriangleShape
 *      fun drawShape(){}
 *  LineShape
 *      fun drawShape(){}
 *
 * 绘制线条
 * Path ：width color path
 * Path ：width color path
 * Path ：width color path
 *
 * 矩形
 *
 * 三角形
 *
 * 圆形
 *
 * 直线
 *
 */

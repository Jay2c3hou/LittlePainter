package com.example.littlepainter.draw.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

abstract class Shape {
    var strokeColor:Int = 0
    var strokeWidth:Int = 0
    var startPoint = PointF(0f,0f)
    var endPoint = PointF(0f,0f)
    val mPaint:Paint by lazy {
        Paint().also {
            it.style = Paint.Style.STROKE
            it.color = strokeColor
            it.strokeWidth  = strokeWidth.toFloat()
        }
    }

    abstract fun drawShape(canvas: Canvas)
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
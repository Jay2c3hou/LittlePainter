package com.example.littlepainter.draw.shape

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.abs

class StraightLineShape : Shape() {
    override fun containsPoint(x: Float, y: Float): Boolean {
        val slope = (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x)
        val intercept = startPoint.y - slope * startPoint.x
        val expectedY = slope * x + intercept
        val epsilon = 0.000001
        return abs(y - expectedY) < epsilon
    }

    override fun drawShape(canvas: Canvas) {
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, mPaint)
    }
}
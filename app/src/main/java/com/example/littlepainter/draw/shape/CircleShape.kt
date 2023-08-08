package com.example.littlepainter.draw.shape

import android.graphics.Canvas
import kotlin.math.abs

class CircleShape : Shape() {
    private var left = 0f
    private var top = 0f
    private var right = 0f
    private var bottom = 0f
    override fun containsPoint(x: Float, y: Float): Boolean {
        val cx = (left + right) / 2
        val cy = (top + bottom) / 2
        val vr = abs(top - bottom) / 2
        val hr = abs(left - right) / 2
        val a = Math.max(vr, hr)
        val b = Math.min(vr, hr)
        val result = ((x - cx) * (x - cx) / (a * a)) + ((y - cy) * (y - cy) / (b * b))
        return result <= 1.0f
    }

    override fun drawShape(canvas: Canvas) {
        left = Math.min(startPoint.x, endPoint.x)
        top = Math.min(startPoint.y, endPoint.y)
        right = Math.max(startPoint.x, endPoint.x)
        bottom = Math.max(startPoint.y, endPoint.y)
        canvas.drawOval(left, top, right, bottom, mPaint)
    }
}
package com.example.littlepainter.draw.shape

import android.graphics.Canvas

class RectangleShape : Shape() {

    var left = 0f
    var top = 0f
    var right = 0f
    var bottom = 0f
    override fun containsPoint(x: Float, y: Float): Boolean {
        return (x in left..right && y in top..bottom)
    }

    override fun drawShape(canvas: Canvas) {
        left = Math.min(startPoint.x, endPoint.x)
        top = Math.min(startPoint.y, endPoint.y)
        right = Math.max(startPoint.x, endPoint.x)
        bottom = Math.max(startPoint.y, endPoint.y)
        canvas.drawRect(left, top, right, bottom, mPaint)
    }
}
package com.example.littlepainter.draw.shape

import android.graphics.Canvas

class RectangleShape:Shape() {
    override fun drawShape(canvas: Canvas) {
        val left = Math.min(startPoint.x, endPoint.x)
        val top = Math.min(startPoint.y, endPoint.y)
        val right = Math.max(startPoint.x, endPoint.x)
        val bottom = Math.max(startPoint.y, endPoint.y)
        canvas.drawRect(left,top,right,bottom, mPaint)
    }
}
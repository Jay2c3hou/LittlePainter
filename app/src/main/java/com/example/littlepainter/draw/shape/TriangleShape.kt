package com.example.littlepainter.draw.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

class TriangleShape: Shape() {
    private var mPath = Path()

    override fun containsPoint(x: Float, y: Float): Boolean {
        val triangleX1 = (startPoint.x + endPoint.x)/2
        val triangleY1 = Math.min(startPoint.y,endPoint.y)

        val triangleX2 = Math.min(startPoint.x, endPoint.x)
        val triangleY2 = Math.max(startPoint.y, endPoint.y)

        val triangleX3 = Math.max(startPoint.x, endPoint.x)
        val triangleY3 = triangleY2

        val area = 0.5 * (-triangleY2 * triangleX3 + triangleX1 * (-triangleY2 + triangleY3) + triangleX2 * (triangleY1 - triangleY3) + triangleX3 * triangleY2)
        val sign = if (area < 0) -1 else 1

        val s = (triangleY1 * triangleX3 - triangleX1 * triangleY3 + (triangleY3 - triangleY1) * x + (triangleX1 - triangleX3) * y) * sign
        val t = (triangleX1 * triangleY2 - triangleY1 * triangleX2 + (triangleY1 - triangleY2) * x + (triangleX2 - triangleX1) * y) * sign

        return s >= 0 && t >= 0 && (s + t) <= 2 * area * sign
    }
    override fun drawShape(canvas: Canvas) {
        val d1x = (startPoint.x + endPoint.x)/2
        val d1y = Math.min(startPoint.y,endPoint.y)

        val d2x = Math.min(startPoint.x, endPoint.x)
        val d2y = Math.max(startPoint.y, endPoint.y)

        val d3x = Math.max(startPoint.x, endPoint.x)
        val d3y = d2y

        mPath.reset()
        mPath.moveTo(d1x,d1y)
        mPath.lineTo(d2x,d2y)
        mPath.lineTo(d3x,d3y)
        mPath.close()

        mPaint.strokeJoin = Paint.Join.ROUND
        canvas.drawPath(mPath,mPaint)
    }
}
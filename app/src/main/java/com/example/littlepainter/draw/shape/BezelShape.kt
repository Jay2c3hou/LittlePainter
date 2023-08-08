package com.example.littlepainter.draw.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import kotlin.math.abs

class BezelShape : Shape() {
    private var mPath = Path()
    override fun containsPoint(x: Float, y: Float): Boolean {
        return false
    }

    override fun drawShape(canvas: Canvas) {
        val distance = abs(startPoint.x - endPoint.x) / 4
        val s0x = Math.min(startPoint.x, endPoint.x)
        val s0y = (startPoint.y + endPoint.y) / 2

        val s1x = s0x + distance
        val s1y = Math.min(startPoint.y, endPoint.y)

        val s2x = s0x + 3 * distance
        val s2y = Math.max(startPoint.y, endPoint.y)

        val s3x = s2x + distance
        val s3y = s0y
        mPath.reset()
        mPath.moveTo(s0x,s0y)
        mPath.cubicTo(s1x,s1y,s2x,s2y,s3x,s3y)

        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawPath(mPath,mPaint)
    }

}
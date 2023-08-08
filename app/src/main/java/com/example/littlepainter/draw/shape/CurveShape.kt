package com.example.littlepainter.draw.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.Region
import android.util.Log

class CurveShape : Shape() {
    private var mPath = Path()

    override fun containsPoint(x: Float, y: Float): Boolean {
        val bounds = RectF()
        /*，使用 computeBounds 方法计算路径 mPath 的边界矩形，并将结果存储在 bounds 对象中。
        参数 true 表示包括路径的控制点在内，以确保边界矩形能够完全包含整个路径。*/
        mPath.computeBounds(bounds, true)
        val region = Region()
        region.setPath(
            mPath,
            Region(
                (bounds.left).toInt(),
                (bounds.top).toInt(),
                (bounds.right).toInt(),
                (bounds.bottom).toInt()
            )
        )

        return region.contains(x.toInt(), y.toInt())
    }

    override var startPoint: PointF = PointF(0f, 0f)
        get() = super.startPoint
        set(value) {
            field = value
            //将路径 mPath 的起始点移动到新的 startPoint 的坐标 (value.x, value.y)
            mPath.moveTo(value.x, value.y)
        }

    override fun drawShape(canvas: Canvas) {
        if (mTranslateX == 0f && mTranslateY == 0f) {
            mPath.lineTo(endPoint.x, endPoint.y)
        }
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.style = Paint.Style.STROKE
        canvas.drawPath(mPath, mPaint)
    }
}
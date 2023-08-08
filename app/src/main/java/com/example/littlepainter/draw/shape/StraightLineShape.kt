package com.example.littlepainter.draw.shape

import android.graphics.Canvas

class StraightLineShape:Shape(){

    override fun drawShape(canvas: Canvas) {
        canvas.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y,mPaint)
    }
}
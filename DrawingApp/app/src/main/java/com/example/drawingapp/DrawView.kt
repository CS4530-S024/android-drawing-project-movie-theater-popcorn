package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var paint: Paint = Paint() // Holds color and style info
    private var bitmap: Bitmap? = null
//    private val mBitmapPaint = Paint(Paint.DITHER_FLAG)

    init {
//        mPaint.isAntiAlias = true
//        mPaint.isDither = true // Makes strokes smoother
//        mPaint.color = Color.GREEN
//        mPaint.style = Paint.Style.STROKE
//        mPaint.strokeJoin = Paint.Join.ROUND
//        mPaint.strokeCap = Paint.Cap.ROUND
//        mPaint.alpha = 0xff // 0xff=255, sets transparency of pen
    }
    fun setPaint(paint: Paint) {
        this.paint = paint
    }

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    // Actual drawing takes place here
    override fun onDraw(canvas: Canvas) {
        Log.d("DrawView", "ON DRAW TO SCREEN")
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
    }
}
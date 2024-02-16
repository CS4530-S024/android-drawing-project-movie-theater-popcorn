package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val touchTolerance = 4f // Used for line smoothing;
    private var mX = 0f // Finger X position
    private var mY = 0f // Finger Y position
    private lateinit var mPath: Path
    private var paint: Paint = Paint() // Holds color and style info
//    private var paths = ArrayList<Pen>() // Stores all strokes done by user
    private var currentPen = Pen(paint.color, paint.strokeWidth.toInt(), Path())
//    private var currentColor = Color.BLACK
//    private var strokeWidth = 20
    private var bitmap: Bitmap? = null
//    private var mCanvas: Canvas? = null
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

    // This instantiates the bitmap and object
//    fun init(height: Int, width: Int) {
//        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        mCanvas = Canvas(mBitmap!!)
//    }

//    fun setColor(color: Int) {
//        currentColor = color
//    }

//    fun setStrokeWidth(width: Int) {
//        strokeWidth = width
//    }

//    fun undo() {
//        if (paths.isNotEmpty()) {
//            paths.removeAt(paths.size - 1)
//            invalidate()
//        }
//    }

    // Returns the current bitmap
//    fun save(): Bitmap? {
//        return mBitmap
//    }

    fun setPaint(paint: Paint) {
        this.paint = paint
    }

    fun getPaint(): Paint {
        return paint
    }

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    fun setCurrentPen(currentPen: Pen) {
        this.currentPen = currentPen
    }

    fun getCurrentPen(): Pen {
        return currentPen
    }

    // Actual drawing takes place here
    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
    }

//    // Creates new stroke (adds to list)
//    private fun touchStart(x: Float, y: Float) {
//        mPath = Path()
//        val fp = Pen(currentColor, strokeWidth, mPath)
//        paths.add(fp)
//
//        // Remove any curve from the path
//        mPath.reset()
//
//        // Sets starting point of line being drawn
//        mPath.moveTo(x, y)
//
//        mX = x
//        mY = y
//    }
//
//    // if the move of finger on the screen is greater than the tolerance then we call the quadTo()
//    // method which smooths the turns we create, by calculating the mean position between the
//    // previous position and current position
//    private fun touchMove(x: Float, y: Float) {
//        val dx = abs(x - mX)
//        val dy = abs(y - mY)
//        if (dx >= touchTolerance || dy >= touchTolerance) {
//            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
//            mX = x
//            mY = y
//        }
//    }
//
//    private fun touchUp() {
//        mPath.lineTo(mX, mY)
//    }
//
    // Determines which touch method is called
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        val x = event.x
//        val y = event.y
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                context.touchStart(x, y)
//                invalidate()
//            }
//            MotionEvent.ACTION_MOVE -> {
//                touchMove(x, y)
//                invalidate()
//            }
//            MotionEvent.ACTION_UP -> {
//                touchUp()
//                invalidate()
//            }
//        }
//        return true
//    }


    // Creates new stroke (adds to list)
    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        currentPen = Pen(paint.color, paint.strokeWidth.toInt(), mPath)

        // Remove any curve from the path
        mPath.reset()

        // Sets starting point of line being drawn
        mPath.moveTo(x, y)

        mX = x
        mY = y
    }

    // if the move of finger on the screen is greater than the tolerance then we call the quadTo()
    // method which smooths the turns we create, by calculating the mean position between the
    // previous position and current position
    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath.lineTo(mX, mY)
    }

    // Determines which touch method is called
    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d("DrawView","touch")
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }
}
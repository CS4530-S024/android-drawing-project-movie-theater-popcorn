package com.example.drawingapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.drawingapp.databinding.FragmentDrawingBinding
import kotlin.math.abs

class DrawingFragment : Fragment()
{
    private lateinit var binding: FragmentDrawingBinding
    private var switchScreenCallback: () -> Unit = {}
    private var paint = Paint()
    private val touchTolerance = 4f // Used for line smoothing;
    private var mX = 0f // Finger X position
    private var mY = 0f // Finger Y position
    private lateinit var currentPath: Path
    private val viewModel : DrawingViewModel by activityViewModels()
    private lateinit var currPen: Pen


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentDrawingBinding.inflate(layoutInflater)

        viewModel.setCurrentPen(Pen(Color.BLACK, 20, Path()))

        viewModel.bitmapCanvas.value!!.drawColor(Color.GRAY)

        paint.strokeWidth = viewModel.currentPen.value!!.strokeWidth.toFloat()
        paint.color = viewModel.currentPen.value!!.color

        binding.drawView.setBitmap(viewModel.bitmap.value!!)
        binding.drawView.setPaint(paint)

//        viewModel.bitmap.observe(viewLifecycleOwner){
//            bitmapCanvas = Canvas(viewModel.bitmap.value!!)
//        }

        //Goes back to the main screen
        binding.backButton.setOnClickListener{
            switchScreenCallback()
        }

        binding.colorSelectButton.setOnClickListener{
            ColorSelectDialogFragment().show(
                childFragmentManager, ColorSelectDialogFragment.TAG)
        }

        binding.drawView.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("touch", "TOUCH DOWN")
                    touchStart(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("touch", "TOUCH MOVE")
                    touchMove(event.x, event.y)
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("touch", "TOUCH UP")
                    touchUp()
                }
            }
            currPen = Pen(paint.color, paint.strokeWidth.toInt(), currentPath)
            viewModel.setCurrentPen(currPen)
            drawPath()

            v?.onTouchEvent(event) ?: true
        }

        // Inflate the layout for this fragment
        return binding.root
    }


    // Creates new stroke (adds to list)
    private fun touchStart(x: Float, y: Float) {
        currentPath = Path()

        // Sets starting point of line being drawn
        currentPath.moveTo(x, y)

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
            currentPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        currentPath.lineTo(mX, mY)
    }


    fun setListener(listener: () -> Unit){
        switchScreenCallback = listener
    }

    private fun drawPath() {
        Log.d("DrawingFragment", "DRAWING PATH")
        val currentPen = viewModel.currentPen.value!!
        //viewModel.bitmapCanvas.value!!.drawPath(currentPen.path, paint)
        binding.drawView.setBitmap(viewModel.bitmap.value!!)
        binding.drawView.setPaint(paint)
        binding.drawView.invalidate()
    }
}
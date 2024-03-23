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
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawingBinding
import kotlin.math.abs

class DrawingFragment : Fragment()
{
    private lateinit var binding: FragmentDrawingBinding
    private var paint = Paint()
    private val touchTolerance = 4f // Used for line smoothing;
    private var mX = 0f // Finger X position
    private var mY = 0f // Finger Y position
    private lateinit var currentPath: Path
    private lateinit var currPen: Pen
    private var eraserOn = false
    private var colorBeforeErase = 0
    private val viewModel by lazy {(activity as MainActivity).viewModel}
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentDrawingBinding.inflate(layoutInflater)
        binding.imageName.setText(viewModel.currentDrawingName.value)

        viewModel.setCurrentPen(Pen(Color.RED, 20, Path()))
        viewModel.setCurrentCap(Paint.Cap.ROUND)
        viewModel.setCurrentJoin(Paint.Join.ROUND)

        paint.style = Paint.Style.STROKE
        binding.drawView.setPaint(paint)
        binding.drawView.setBitmap(viewModel.bitmap.value!!)

        //Goes back to the main screen
        binding.backButton.setOnClickListener{
            findNavController().navigate(R.id.backButton)
        }

        binding.penSizeSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            /*
                Used to change the pen's drawing width
             */
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                currPen.strokeWidth = (progress + 1) * 10
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
            }
        })

        binding.colorSelectButton.setOnClickListener{
            ColorSelectDialogFragment().show(
                childFragmentManager, ColorSelectDialogFragment.TAG)
        }

        binding.penButton.setOnClickListener{
            if(eraserOn)
            {
                binding.colorSelectButton.setEnabled(true)
                viewModel.currentPen.value!!.color = colorBeforeErase
                eraserOn = false
            }
        }

        binding.eraserButton.setOnClickListener{
            if(!eraserOn)
            {
                binding.colorSelectButton.setEnabled(false)
                colorBeforeErase = viewModel.currentPen.value!!.color
                viewModel.currentPen.value!!.color = Color.WHITE
                eraserOn = true
            }
        }

        binding.shapeButton1.setOnClickListener{
            viewModel.setCurrentCap(Paint.Cap.ROUND)
            viewModel.setCurrentJoin(Paint.Join.ROUND)
        }

        binding.shapeButton2.setOnClickListener{
            viewModel.setCurrentCap(Paint.Cap.SQUARE)
            viewModel.setCurrentJoin(Paint.Join.BEVEL)
        }

        binding.shapeButton3.setOnClickListener{
            viewModel.setCurrentCap(Paint.Cap.BUTT)
            viewModel.setCurrentJoin(Paint.Join.MITER)
        }

        binding.saveButton.setOnClickListener{
            if(binding.imageName.text.toString() != "") {
                context?.filesDir?.let { it1 ->
                    viewModel.saveDrawing(
                        binding.imageName.text.toString(),
                        it1.absolutePath
                    )
                }
                viewModel.setCurrentDrawingName(binding.imageName.text.toString())
                Toast.makeText(
                    activity, "Saved!",
                    Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(
                    activity, "Please enter a name before saving",
                    Toast.LENGTH_LONG).show();
            }
        }

        binding.drawView.setOnTouchListener { _, event ->
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
            currPen = Pen(viewModel.currentPen.value!!.color,
                viewModel.currentPen.value!!.strokeWidth, currentPath)

            viewModel.setCurrentPen(currPen)
            drawPath()

            true
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

    private fun drawPath() {
        Log.d("DrawingFragment", "DRAWING PATH")

        val currentPen = viewModel.currentPen.value!!
        paint.color = currentPen.color
        paint.strokeWidth = currentPen.strokeWidth.toFloat()
        paint.strokeCap = viewModel.currentCap.value!!
        paint.strokeJoin = viewModel.currentJoin.value!!

        viewModel.bitmapCanvas.value!!.drawPath(currentPen.path, paint)

        binding.drawView.setBitmap(viewModel.bitmap.value!!)
        binding.drawView.setPaint(paint)
        binding.drawView.invalidate()
    }
}
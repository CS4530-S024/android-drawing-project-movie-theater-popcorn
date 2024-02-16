package com.example.drawingapp

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.fragment.app.activityViewModels
import com.example.drawingapp.databinding.FragmentDrawingBinding

class DrawingFragment : Fragment()
{
    private lateinit var binding: FragmentDrawingBinding
    private var switchScreenCallback: () -> Unit = {}
    private var paint = Paint()
    private lateinit var bitmapCanvas: Canvas
    private val viewModel : DrawingViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentDrawingBinding.inflate(layoutInflater)

        viewModel.setCurrentPen(Pen(Color.BLACK, 20, Path()))
        binding.drawView.setCurrentPen(viewModel.currentPen.value!!)
        viewModel.setColor(Color.BLACK.toColor())
        viewModel.setPenWidth(20)
        bitmapCanvas = Canvas(viewModel.bitmap.value!!)
        bitmapCanvas.drawColor(Color.WHITE)

        viewModel.color.observe(viewLifecycleOwner){
            val tempPaint = binding.drawView.getPaint()
            tempPaint.color = viewModel.color.value!!.toArgb()
            binding.drawView.setPaint(tempPaint)
            drawPath(viewModel.currentPen.value!!)
        }

        viewModel.currentPen.observe(viewLifecycleOwner){
            drawPath(it)
        }

        viewModel.bitmap.observe(viewLifecycleOwner){
            bitmapCanvas = Canvas(viewModel.bitmap.value!!)
        }

        //Goes back to the main screen
        binding.backButton.setOnClickListener{
            switchScreenCallback()
        }

        binding.colorSelectButton.setOnClickListener{
            ColorSelectDialogFragment().show(
                childFragmentManager, ColorSelectDialogFragment.TAG)
        }

        binding.drawView.setOnClickListener {
            Log.d("Drawing Fragment", "setOnClickListener test")
            viewModel.setCurrentPen(binding.drawView.getCurrentPen())
            drawPath(viewModel.currentPen.value!!)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


    fun setListener(listener: () -> Unit){
        switchScreenCallback = listener
    }

    private fun drawPath(currentPen: Pen) {
        Log.d("DrawingFragment", "in drawPath")
        paint.strokeWidth = currentPen.strokeWidth.toFloat()
        bitmapCanvas.drawPath(currentPen.path, paint)
        binding.drawView.setBitmap(viewModel.bitmap.value!!)
        binding.drawView.invalidate()
    }
}
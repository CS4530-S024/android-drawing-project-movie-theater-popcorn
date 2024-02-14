package com.example.drawingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.drawingapp.databinding.FragmentDrawingBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DrawingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrawingFragment : Fragment()
{
    private lateinit var binding: FragmentDrawingBinding
    private var switchScreenCallback: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentDrawingBinding.inflate(layoutInflater)

        //Goes back to the main screen
        binding.backButton.setOnClickListener{
            switchScreenCallback()
        }

        // TODO: Move to DrawingFragment; just used for testing
        val paint: DrawView = binding.drawView
        paint.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                paint.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = paint.measuredWidth
                val height = paint.measuredHeight
                paint.init(height, width)
            }
      })


        // Inflate the layout for this fragment
        return binding.root
    }


    fun setListener(listener: () -> Unit){
        switchScreenCallback = listener
    }

}
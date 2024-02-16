package com.example.drawingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drawingapp.databinding.FragmentHomeScreenBinding


/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreenFragment : Fragment()
{
    private lateinit var binding: FragmentHomeScreenBinding
    private var switchScreenCallback: () -> Unit = {}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)

        binding.newCanvasButton.setOnClickListener{
            switchScreenCallback()
            //do other things for creating a new canvas.
        }
        // Inflate the layout for this fragment
        return binding.root
    }


    fun setListener(listener: () -> Unit){
        switchScreenCallback = listener
    }

}
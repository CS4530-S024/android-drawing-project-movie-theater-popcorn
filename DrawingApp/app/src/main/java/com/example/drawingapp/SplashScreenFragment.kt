package com.example.drawingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drawingapp.databinding.FragmentSplashScreenBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SplashScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashScreenFragment : Fragment()
{
    private lateinit var binding: FragmentSplashScreenBinding
    private var switchScreenCallback: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)

        binding.imageView.setOnClickListener{
            switchScreenCallback()
        }



        // Inflate the layout for this fragment
        return binding.root
    }

    fun setListener(listener: () -> Unit){
        switchScreenCallback = listener
    }
}
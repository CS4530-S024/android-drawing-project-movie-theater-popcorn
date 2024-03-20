package com.example.drawingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentHomeScreenBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreenFragment : Fragment()
{
    private lateinit var binding: FragmentHomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)

        binding.newCanvasButton.setOnClickListener{
            findNavController().navigate(R.id.newCanvasButton)
        }

        binding.existingDrawing.setOnClickListener{
            findNavController().navigate(R.id.existingDrawing)
            //TODO: grab drawing from db and import into drawingfragment
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}
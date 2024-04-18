package com.example.drawingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.drawingapp.databinding.FragmentShareDrawingBinding
class ShareDrawingFragment : DialogFragment() {
    private lateinit var binding: FragmentShareDrawingBinding
    private val viewModel by lazy{(activity as MainActivity).viewModel}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShareDrawingBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object {
        const val TAG = "ShareDrawingDialog"
    }
}
package com.example.drawingapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.drawingapp.databinding.FragmentColorSelectBinding
import com.example.drawingapp.databinding.FragmentDrawingBinding

class ColorSelectDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentColorSelectBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorSelectBinding.inflate(layoutInflater)
        return binding.root

    }

    companion object {
        const val TAG = "ColorSelectDialog"
    }
}
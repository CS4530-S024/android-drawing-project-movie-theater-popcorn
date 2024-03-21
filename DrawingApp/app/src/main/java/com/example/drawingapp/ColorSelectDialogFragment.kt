package com.example.drawingapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.drawingapp.databinding.FragmentColorSelectBinding

class ColorSelectDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentColorSelectBinding
    private val viewModel by lazy {(activity as MainActivity).viewModel}

    private var prevColor : Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorSelectBinding.inflate(layoutInflater)

        // Set initial seek bar values to the values for the current color
        binding.redSeekBarValue.text = Color.red(viewModel.currentPen.value!!.color).toString()
        binding.greenSeekBarValue.text = Color.green(viewModel.currentPen.value!!.color).toString()
        binding.blueSeekBarValue.text = Color.blue(viewModel.currentPen.value!!.color).toString()

        binding.redSeekBar.progress = (binding.redSeekBarValue.text as String).toInt()
        binding.greenSeekBar.progress = (binding.greenSeekBarValue.text as String).toInt()
        binding.blueSeekBar.progress = (binding.blueSeekBarValue.text as String).toInt()

        prevColor = viewModel.currentPen.value!!.color
        binding.prevPenColor.setBackgroundColor(prevColor)
        binding.newPenColor.setBackgroundColor(prevColor)

        binding.redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.redSeekBarValue.text = progress.toString()

                val currPen = viewModel.currentPen.value!!
                currPen.color = Color.rgb(progress, currPen.color.green,
                    currPen.color.blue)
                viewModel.setCurrentPen(currPen)

                binding.newPenColor.setBackgroundColor(Color.rgb(progress,currPen.color.green, currPen.color.blue))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.greenSeekBarValue.text = progress.toString()

                val currPen = viewModel.currentPen.value!!
                currPen.color = Color.rgb(currPen.color.red, progress,
                    currPen.color.blue)
                viewModel.setCurrentPen(currPen)

                binding.newPenColor.setBackgroundColor(Color.rgb(currPen.color.red, progress, currPen.color.blue))

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.blueSeekBarValue.text = progress.toString()

                val currPen = viewModel.currentPen.value!!
                currPen.color = Color.rgb(currPen.color.red,
                    currPen.color.green, progress)
                viewModel.setCurrentPen(currPen)

                binding.newPenColor.setBackgroundColor(Color.rgb(currPen.color.red, currPen.color.green, progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.prevPenColor.setOnClickListener{
            val currPen = viewModel.currentPen.value!!
            currPen.color = prevColor
        }
        binding.newPenColor.setOnClickListener{
            val currPen = viewModel.currentPen.value!!
            currPen.color = currPen.color
        }

        return binding.root
    }

    companion object {
        const val TAG = "ColorSelectDialog"
    }
}
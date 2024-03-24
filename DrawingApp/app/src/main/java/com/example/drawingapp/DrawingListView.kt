package com.example.drawingapp

import android.graphics.*
import android.os.Bundle
import android.util.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawingCompBinding

class DrawingListView : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDrawingCompBinding.inflate(layoutInflater)
        val viewModel: DrawingViewModel by lazy {(activity as MainActivity).viewModel}
        //ComposeView gives us a `Composable` context to run functions in
        binding.composeView.setContent {
            DrawingComposable(Modifier.padding(16.dp), viewModel = viewModel,
                onClick = { findNavController().navigate(R.id.action_go_back_to_home_screen) }) {
                    findNavController().navigate(R.id.action_open_saved_drawing)
            }
        }

        return binding.root
    }


    @Composable
    fun DrawingComposable(modifier: Modifier = Modifier,
                          viewModel: DrawingViewModel = viewModel(
                              viewModelStoreOwner = LocalContext.current.findActivity()
                          ),
                          onClick: ()->Unit, onClickFile: () -> Unit)
    {

        val currentDrawings by viewModel.allDrawings.collectAsState(listOf())
        Box(modifier){
            Button(onClick = onClick) {
            Text("Go Back")
        }
            LazyColumn(Modifier.padding(35.dp)) {
                items(currentDrawings){ drawing ->
                    viewModel.loadDrawing(drawing.filePath)
                   ExistingDrawingItem(
                       drawing = drawing,
                       bitmap = viewModel.bitmap.value!!,
                       onClick = onClickFile,
                       viewModel = viewModel
                    )
                }
            }
        }
    }

    @Composable
    fun ExistingDrawingItem(
        drawing: DrawingData,
        modifier: Modifier = Modifier,
        bitmap: Bitmap,
        onClick: () -> Unit,
        viewModel: DrawingViewModel
    )
    {
        Row(Modifier.padding(16.dp)) {
           Card {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = ""
                )

                Log.d("File path", "${drawing.filePath}")
                Button(onClick = {
                    onClick.invoke()
                    viewModel.loadDrawing(drawing.filePath)
                    viewModel.setCurrentDrawingName(drawing.fileName)
                }) {
                    Text(text = drawing.fileName)
                }
            }

        }
    }
}
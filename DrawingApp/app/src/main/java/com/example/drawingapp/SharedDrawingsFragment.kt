package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawingCompBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class SharedDrawingsFragment : Fragment() {
    private val viewModel: DrawingViewModel by lazy { (activity as MainActivity).viewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDrawingCompBinding.inflate(layoutInflater)

        binding.composeView.setContent {
            SharedDrawingsPage()
        }
        return binding.root
    }

    @Composable
    fun SharedDrawingsPage() {
        Column {
            Box {
                Button(onClick = {
                    findNavController().navigate(
                        R.id.action_sharedDrawingsFragment_to_fragment_home_screen
                    )
                }) { Text("Go Back") }
            }

            Column {
                for (i in 0 until viewModel.getTitlesSize()) {
                    DrawingItem(
                        title = viewModel.getTitle(i),
                        author = viewModel.getAuthor(i),
                        bitmap = viewModel.getBitmap(i)
                    )
                }
            }
        }
    }

    @Composable
    fun DrawingItem(
        title: String,
        author: String,
        bitmap: Bitmap
    ) {
        Row(Modifier.padding(16.dp)) {
            Card {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = ""
                )
                Text(
                    text = "Title: $title"
                )
                Text(
                    text = "Author: $author"
                )
                Button(onClick = {
                    findNavController().navigate(
                        R.id.action_sharedDrawingsFragment_to_fragment_drawing
                    )
                    context?.filesDir?.absolutePath?.let {
                        viewModel.saveDrawing(
                            title,
                            it, bitmap
                        )
                    }
                    context?.filesDir?.absolutePath?.let { viewModel.loadDrawing("${it}/${title}") }
                    viewModel.setCurrentDrawingName(title)
                }) {
                    Text("Download")
                }
            }
        }
    }

}
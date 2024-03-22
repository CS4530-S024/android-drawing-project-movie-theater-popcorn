package com.example.drawingapp

import android.hardware.lights.Light
import android.hardware.lights.LightState
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawingCompBinding

@Composable
fun ExistingDrawingItem(drawing: DrawingData, modifier: Modifier = Modifier)
{
    Column {
        //TODO: Get info from data base
        //Image(bitmap = "", contentDescription = "")
        Text(text = drawing.fileName)
        
    }
}

@Composable
fun ExistingDrawings(drawings: List<DrawingData>, modifier: Modifier = Modifier)
{
    Box(modifier){
        LazyColumn(Modifier.fillMaxWidth()) {
            items(drawings){ drawing ->
                ExistingDrawingItem(
                    drawing = drawing

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun test(){
    ExistingDrawingItem(drawing = DrawingData("testName", "./somewhere"))
}
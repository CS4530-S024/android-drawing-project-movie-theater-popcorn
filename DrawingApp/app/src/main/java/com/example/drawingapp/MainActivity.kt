package com.example.drawingapp

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.viewModels
import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private val binding: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    val viewModel: DrawingViewModel by viewModels{
        DrawingViewModelFactory((application as DrawingApplication).drawingRepository)}

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        //Code for the splash screen
        Thread.sleep(2000)
        installSplashScreen()

        setContentView(binding.root)
    }

//    companion object {
//      init {
//         System.loadLibrary("blurDrawing")
//      }
//    }
}

internal fun Context.findActivity(): ComponentActivity
{
    var context = this
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}
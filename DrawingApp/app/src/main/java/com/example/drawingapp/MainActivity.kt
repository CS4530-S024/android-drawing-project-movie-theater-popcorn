package com.example.drawingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private val binding: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        //Code for the splash screen
        Thread.sleep(2000)
        installSplashScreen()

//        setContentView(R.layout.activity_main)

//        val homeFrag = HomeScreenFragment()
//        homeFrag.setListener(){
//                val fragDraw = DrawingFragment()
//                fragDraw.setListener {
//                    supportFragmentManager.popBackStack()
//                }
//
//                val fTrans = supportFragmentManager.beginTransaction()
//                fTrans.replace(R.id.fragmentContainerView, fragDraw)
//                fTrans.addToBackStack(null)
//                fTrans.commit()
//        }
//
//        val fTrans = supportFragmentManager.beginTransaction()
//        fTrans.replace(R.id.fragmentContainerView, homeFrag)
//        fTrans.commit()

        setContentView(binding.root)
    }
}
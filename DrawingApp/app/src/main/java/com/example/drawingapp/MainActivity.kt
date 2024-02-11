package com.example.drawingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private val binding: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: Move to DrawingFragment; just used for testing
        val paint: DrawView = findViewById(R.id.draw_view)
        paint.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                paint.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = paint.measuredWidth
                val height = paint.measuredHeight
                paint.init(height, width)

        val fragSplash = SplashScreenFragment()
        fragSplash.setListener(){
            val fragHome = HomeScreenFragment()
            fragHome.setListener {
                val fragDraw = DrawingFragment()
                fragDraw.setListener {
                    supportFragmentManager.popBackStack()
                }

                val fTrans = supportFragmentManager.beginTransaction()
                fTrans.replace(R.id.fragmentContainerView, fragDraw)
                fTrans.addToBackStack(null)
                fTrans.commit()
            }

            val fTrans = supportFragmentManager.beginTransaction()
            fTrans.replace(R.id.fragmentContainerView, fragHome)
            //ftrans.addToBackStack(null) //does a back button to the splash screen, we don't need it on the home screen.
            fTrans.commit()
        }

        val fTrans = supportFragmentManager.beginTransaction()
        fTrans.replace(R.id.fragmentContainerView, fragSplash)
        fTrans.commit()

        setContentView(binding.root)

    }
}
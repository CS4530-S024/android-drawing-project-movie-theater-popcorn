package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrawingViewModel: ViewModel() {
    private val _bitmap : MutableLiveData<Bitmap> =
        MutableLiveData(Bitmap.createBitmap(1200, 1200, Bitmap.Config.ARGB_8888))
    val bitmap = _bitmap as LiveData<Bitmap>

    private val _bitmapCanvas : MutableLiveData<Canvas> = MutableLiveData(Canvas(bitmap.value!!))
    val bitmapCanvas = _bitmapCanvas as LiveData<Canvas>


    private val _currentPen = MutableLiveData<Pen>()
    var currentPen = _currentPen as LiveData<Pen>

    fun setCurrentPen(currentPen: Pen) {
        this._currentPen.value = currentPen
    }

    fun setBitmap(bitmap: Bitmap) {
        this._bitmap.value = bitmap
    }

}
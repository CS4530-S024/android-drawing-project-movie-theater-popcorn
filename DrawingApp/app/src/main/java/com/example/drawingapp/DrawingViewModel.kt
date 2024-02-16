package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrawingViewModel: ViewModel() {
    //Model
    private val _color : MutableLiveData<Color> =
        MutableLiveData(Color.valueOf(1f, 1f, 0f))
    val color  = _color as LiveData<Color>

    private val _penWidth = MutableLiveData<Int>()
    val penWidth = _penWidth as LiveData<Int>

    private val _bitmap : MutableLiveData<Bitmap> =
        MutableLiveData(Bitmap.createBitmap(1200, 1200, Bitmap.Config.ARGB_8888))
    val bitmap = _bitmap as LiveData<Bitmap>

    private val _currentPen = MutableLiveData<Pen>()
    var currentPen = _currentPen as LiveData<Pen>

    fun setCurrentPen(currentPen: Pen) {
        this._currentPen.value = currentPen
    }

    fun setColor(color: Color) {
        this._color.value = color
    }

    fun setBitmap(bitmap: Bitmap) {
        this._bitmap.value = bitmap
    }

    fun setPenWidth(penWidth: Int) {
        this._penWidth.value = penWidth
    }
}
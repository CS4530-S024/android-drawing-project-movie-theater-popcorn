package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
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

    private val _currentJoin = MutableLiveData<Paint.Join>()
    var currentJoin = _currentJoin as LiveData<Paint.Join>

    private val _currentCap = MutableLiveData<Paint.Cap>()
    var currentCap = _currentCap as LiveData<Paint.Cap>

    fun setCurrentPen(currentPen: Pen) {
        this._currentPen.value = currentPen
    }

    fun setCurrentJoin(currentJoin: Paint.Join) {
        this._currentJoin.value = currentJoin
    }

    fun setCurrentCap(currentCap: Paint.Cap) {
        this._currentCap.value = currentCap
    }

}
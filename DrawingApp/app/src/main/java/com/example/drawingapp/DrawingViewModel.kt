package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow

class DrawingViewModel(private val repository: DrawingRepository): ViewModel() {
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

    val allDrawings: Flow<List<DrawingData>> = repository.allDrawings
    fun saveDrawing(fileName: String, filePath: String){
        Log.e("VM", "Saving drawing $fileName")
        repository.saveDrawing(fileName, filePath)
    }

}

// This factory class allows us to define custom constructors for the view model
class DrawingViewModelFactory(private val repository: DrawingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrawingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrawingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

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

/**
 * Hold the information of the current state
 * of the drawing.
 */
class DrawingViewModel(private val repository: DrawingRepository): ViewModel() {
private val _bitmap : MutableLiveData<Bitmap> =
        MutableLiveData(Bitmap.createBitmap(1200, 1200, Bitmap.Config.ARGB_8888))
    var bitmap = _bitmap as LiveData<Bitmap>

    private val _bitmapCanvas : MutableLiveData<Canvas> = MutableLiveData(Canvas(bitmap.value!!))
    val bitmapCanvas = _bitmapCanvas as LiveData<Canvas>

    private val _currentPen = MutableLiveData<Pen>()
    var currentPen = _currentPen as LiveData<Pen>

    private val _currentJoin = MutableLiveData<Paint.Join>()
    var currentJoin = _currentJoin as LiveData<Paint.Join>

    private val _currentCap = MutableLiveData<Paint.Cap>()
    var currentCap = _currentCap as LiveData<Paint.Cap>

    private val _currentDrawingName = MutableLiveData("")
    val currentDrawingName = _currentDrawingName as LiveData<String>

    private external fun invertBitmap(bitmapDrawing: Bitmap)
    private external fun staticImage(bitmap: Bitmap)

    private val _titles = MutableLiveData(mutableListOf<String>())
    val titles = _titles as LiveData<List<String>>

    private val _authors = MutableLiveData(mutableListOf<String>())
    val authors = _authors as LiveData<List<String>>

    private val _bitmaps = MutableLiveData(mutableListOf<Bitmap>())
    val bitmaps = _bitmaps as LiveData<List<Bitmap>>

    fun addTitle(title: String)
    {
        _titles.value!!.add(title)
    }

    fun addAuthor(author: String)
    {
        _authors.value!!.add(author)
    }

    fun addBitmap(bitmap: Bitmap)
    {
        _bitmaps.value!!.add(bitmap)
    }

    fun getTitle(i: Int) : String
    {
        return titles.value!![i]
    }

    fun getAuthor(i: Int) : String
    {
        return authors.value!![i]
    }

    fun getBitmap(i: Int) : Bitmap
    {
        return bitmaps.value!![i]
    }

    fun getTitlesSize() : Int
    {
        return titles.value!!.size
    }

    fun setCurrentDrawingName(newName: String) {
        this._currentDrawingName.value = newName
    }

    fun setCurrentPen(currentPen: Pen) {
        this._currentPen.value = currentPen
    }

    fun setCurrentJoin(currentJoin: Paint.Join) {
        this._currentJoin.value = currentJoin
    }

    fun setCurrentCap(currentCap: Paint.Cap) {
        this._currentCap.value = currentCap
    }

    // Use this to get allDrawings inside a composable:
    val allDrawings: Flow<List<DrawingData>> = repository.allDrawings

    fun saveDrawing(fileName: String, filePath: String, bitmap: Bitmap){
        Log.e("VM", "Saving drawing $fileName")
        repository.saveDrawing(fileName, filePath, bitmap)
    }

    fun loadDrawing(filePath: String) {
        val newBitmap = repository.loadDrawing(filePath)
        _bitmap.value = newBitmap.copy(Bitmap.Config.ARGB_8888, true)
        _bitmapCanvas.value = Canvas(bitmap.value!!)
    }

    fun invertDrawing() {
        invertBitmap(_bitmap.value!!)
    }
    fun addNoise()
    {
        staticImage(_bitmap.value!!)
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

package com.example.drawingapp
import android.util.Log
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class DrawingRepository(private val scope: CoroutineScope,
                        private val dao: DrawingDAO) {

    //updated with the DB is modified
    val allDrawings = dao.allDrawings()

    fun saveDrawing(fileName: String, filePath: String) {
        scope.launch {
            Log.e("REPO", "Saving drawing $fileName")
            val savedDrawing = DrawingData(fileName, filePath)
            dao.addDrawingData(savedDrawing)
            Log.e("REPO", "told the DAO")
        }
    }
}
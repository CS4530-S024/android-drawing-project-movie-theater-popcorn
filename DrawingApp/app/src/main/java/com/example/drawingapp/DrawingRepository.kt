package com.example.drawingapp
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class DrawingRepository(private val scope: CoroutineScope,
                        private val dao: DrawingDAO) {

    //updated with the DB is modified
    val allDrawings = dao.allDrawings()

    fun saveDrawing(fileName: String, filePath: String, bitmap: Bitmap) {
        scope.launch {
            Log.e("REPO", "Saving drawing $fileName")
            val savedDrawing = DrawingData(fileName, filePath)
            dao.addDrawingData(savedDrawing)

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            val bitmapData = bos.toByteArray();
            val encodedData: String = Base64.encodeToString(bitmapData, Base64.DEFAULT)

            //create a file to write bitmap data
            val file = File(filePath, fileName);
            file.createNewFile();
            file.writeText(encodedData);
        }
    }
}
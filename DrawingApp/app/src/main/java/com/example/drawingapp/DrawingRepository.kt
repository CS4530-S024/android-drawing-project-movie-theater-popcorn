package com.example.drawingapp
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File

class DrawingRepository(private val scope: CoroutineScope,
                        private val dao: DrawingDAO) {

    //updated with the DB is modified
    val allDrawings = dao.allDrawings()

    fun saveDrawing(fileName: String, filePath: String, bitmap: Bitmap) {
        scope.launch {
            Log.e("REPO", "Saving drawing $fileName")
            if(dao.drawingIsStored(fileName))
            {
                dao.deleteDrawing(fileName)
            }
            val savedDrawing = DrawingData(fileName, filePath + "/${fileName}")
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

    fun loadDrawing(filePath: String): Bitmap {
        val file = File(filePath)
        val text = file.readText()
        val imageBytes = Base64.decode(text, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}
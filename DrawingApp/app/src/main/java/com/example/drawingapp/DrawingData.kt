package com.example.drawingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

//Defines a SQLITE table
@Entity(tableName="drawing")
data class DrawingData(var fileName: String,
                       var filePath: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 // integer primary key for the DB
}
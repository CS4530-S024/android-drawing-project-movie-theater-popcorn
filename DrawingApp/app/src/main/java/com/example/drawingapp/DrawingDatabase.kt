package com.example.drawingapp

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

//this is a DB, we have 1 entity (so we'll get 1 table in SQLite)
//the version stuff is for managing DB migrations
@Database(entities= [DrawingData::class], version = 1, exportSchema = false)
abstract class DrawingDatabase : RoomDatabase(){
    abstract fun drawingDao(): DrawingDAO
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DrawingDatabase? = null

        //When we want a DB we call this (basically static) method
        //val theDB = DrawingDatabase.getDatabase(myContext)
        fun getDatabase(context: Context): DrawingDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                //if another thread initialized this before we got the lock
                //return the object they created
                if(INSTANCE != null) return INSTANCE!!
                //otherwise we're the first thread here, so create the DB
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrawingDatabase::class.java,
                    "drawing_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


@Dao
interface DrawingDAO {
    //marked as suspend so the thread can yield in case the DB update is slow
    @Insert
    suspend fun addDrawingData(data: DrawingData)

    @Query("SELECT * from drawing")
    fun allDrawings() : Flow<List<DrawingData>>
}
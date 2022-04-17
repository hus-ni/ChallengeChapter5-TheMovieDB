package com.muhammadhusniabdillah.challengechapter5.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muhammadhusniabdillah.challengechapter5.data.entity.Login

@Database(entities = [Login::class], version = 1, exportSchema = false)
abstract class ChapterFiveDatabase : RoomDatabase() {


    companion object {
        @Volatile
        private var INSTANCE: ChapterFiveDatabase? = null
    }

    fun getDatabase(context: Context): ChapterFiveDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ChapterFiveDatabase::class.java,
                "chapter5_database"
            ).build()
            INSTANCE = instance

            instance
        }
    }
}
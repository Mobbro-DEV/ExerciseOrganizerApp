package com.example.organizer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.organizer.data.dao.ExerciseDao
import com.example.organizer.data.entity.Exercise

@Database(
    version = 1,
    entities = [
        Exercise::class
    ],
    exportSchema = false
)
abstract class OrganizerDatabase : RoomDatabase() {
    abstract val dao: ExerciseDao

    companion object {
        @Volatile private var INSTANCE: OrganizerDatabase? = null

        fun get(context: Context): OrganizerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrganizerDatabase::class.java,
                    "organizer_db"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
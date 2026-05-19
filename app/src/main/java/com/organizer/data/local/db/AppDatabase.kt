package com.organizer.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.db.entities.CategoryEntity

@Database(
    version = 1,
    entities = [
        CategoryEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: CategoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
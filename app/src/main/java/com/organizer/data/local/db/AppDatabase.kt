package com.organizer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.dao.ExerciseDao
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity

@Database(
    version = 1,
    entities = [
        CategoryEntity::class,
        ExerciseEntity::class,
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val exerciseDao: ExerciseDao
}

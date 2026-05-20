package com.organizer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.db.entities.CategoryEntity

@Database(
    version = 1,
    entities = [CategoryEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: CategoryDao
}

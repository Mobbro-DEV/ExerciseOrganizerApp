package com.organizer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.dao.ExerciseDao
import com.organizer.data.local.dao.WorkoutDao
import com.organizer.data.local.dao.WorkoutExerciseDao
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.db.entities.WorkoutEntity
import com.organizer.data.local.db.entities.WorkoutExerciseEntity

@Database(
    version = 1,
    entities = [
        CategoryEntity::class,
        ExerciseEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val exerciseDao: ExerciseDao
    abstract val workoutDao: WorkoutDao
    abstract val workoutExerciseDao: WorkoutExerciseDao
}

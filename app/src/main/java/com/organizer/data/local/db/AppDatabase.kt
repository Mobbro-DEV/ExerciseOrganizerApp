package com.organizer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.dao.ExerciseCategoryDao
import com.organizer.data.local.dao.ExerciseDao
import com.organizer.data.local.dao.WorkoutDao
import com.organizer.data.local.dao.WorkoutExerciseDao
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseCategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.db.entities.WorkoutEntity
import com.organizer.data.local.db.entities.WorkoutExerciseEntity
import com.organizer.data.mapper.ListConverters

@Database(
    version = 1,
    entities = [
        CategoryEntity::class,
        ExerciseEntity::class,
        ExerciseCategoryEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class
    ],
    exportSchema = false
)
@TypeConverters(ListConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val exerciseDao: ExerciseDao
    abstract val exerciseCategoryDao: ExerciseCategoryDao
    abstract val workoutDao: WorkoutDao
    abstract val workoutExerciseDao: WorkoutExerciseDao
}

package com.organizer.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.organizer.data.local.db.entities.WorkoutExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(workoutExerciseEntity: WorkoutExerciseEntity)

    @Query("SELECT * FROM workout_exercise")
    fun getAll(): Flow<List<WorkoutExerciseEntity>>

    @Query("SELECT exerciseId FROM workout_exercise WHERE workoutId = :workoutId")
    fun getExerciseIdsByWorkout(workoutId: Long): Flow<List<Long>>

    @Delete
    suspend fun delete(workoutExercise: WorkoutExerciseEntity)
}

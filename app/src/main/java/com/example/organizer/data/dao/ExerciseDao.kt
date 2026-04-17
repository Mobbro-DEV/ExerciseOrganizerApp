package com.example.organizer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.organizer.data.entity.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(vararg exercises: Exercise)

    @Query("SELECT * FROM Exercise")
    fun getAll(): Flow<List<Exercise>>

    @Delete
    suspend fun delete(exercise: Exercise)
}

package com.example.organizer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.organizer.data.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg exercises: ExerciseEntity)

    @Query("SELECT * FROM ExerciseEntity")
    fun getAll(): Flow<List<ExerciseEntity>>

    @Delete
    suspend fun delete(exercise: ExerciseEntity)
}

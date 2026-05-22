package com.organizer.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.organizer.data.local.db.entities.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercises: List<ExerciseEntity>)

    @Query("SELECT * FROM exercise")
    fun getAll(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercise")
    suspend fun getAllOnce(): List<ExerciseEntity>

    @Query("SELECT * FROM exercise WHERE exerciseId = :id")
    suspend fun getById(id: Long): ExerciseEntity?

    @Delete
    suspend fun delete(exercise: ExerciseEntity)
}

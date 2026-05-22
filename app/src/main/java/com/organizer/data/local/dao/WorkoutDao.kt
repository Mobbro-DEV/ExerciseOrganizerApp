package com.organizer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.organizer.data.local.db.entities.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(workoutEntity: WorkoutEntity)

    @Query("SELECT * FROM workout")
    fun getAll(): Flow<List<WorkoutEntity>>

    @Query("DELETE FROM workout WHERE workoutId = :id")
    suspend fun delete(id: Long)
}

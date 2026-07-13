package com.organizer.data.local.repo

import com.organizer.data.local.dao.ExerciseDao
import com.organizer.data.local.db.entities.ExerciseEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ExerciseLocalDataSource @Inject constructor(
    private val dao: ExerciseDao
) {
    suspend fun insertAll(exercises: List<ExerciseEntity>) {
        dao.insertAll(exercises)
    }

    suspend fun updateAll(exercises: List<ExerciseEntity>) {
        dao.updateAll(exercises)
    }

    suspend fun insert(exercise: ExerciseEntity) {
        dao.insert(exercise)
    }

    fun getAll(): Flow<List<ExerciseEntity>> {
        return dao.getAll()
    }

    suspend fun getAllOnce(): List<ExerciseEntity> {
        return dao.getAllOnce()
    }

    fun getExercisesByCategory(categoryId: Long): Flow<List<ExerciseEntity>> {
        return dao.getExercisesByCategory(categoryId)
    }

    fun getExercisesByIds(exerciseIds: List<Long>): Flow<List<ExerciseEntity>> {
        return dao.getExercisesByIds(exerciseIds)
    }

    fun getCustomExercises(): Flow<List<ExerciseEntity>> {
        return dao.getCustomExercises()
    }

    fun getById(id: Long): Flow<ExerciseEntity?> {
        return dao.getById(id)
    }

    suspend fun delete(exercise: ExerciseEntity) {
        dao.delete(exercise)
    }
}
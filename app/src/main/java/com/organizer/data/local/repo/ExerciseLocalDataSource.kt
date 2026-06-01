package com.organizer.data.local.repo

import com.organizer.data.local.dao.ExerciseDao
import com.organizer.data.local.db.entities.ExerciseEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ExerciseLocalDataSource @Inject constructor(
    private val dao: ExerciseDao
) {
    suspend fun insert(exercises: List<ExerciseEntity>) {
        dao.insert(exercises)
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

    suspend fun getById(id: Long): ExerciseEntity? {
        return dao.getById(id)
    }

    suspend fun delete(exercise: ExerciseEntity) {
        dao.delete(exercise)
    }
}
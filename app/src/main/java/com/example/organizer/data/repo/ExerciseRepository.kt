package com.example.organizer.data.repo

import com.example.organizer.data.dao.ExerciseDao
import com.example.organizer.data.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val dao: ExerciseDao) {
    suspend fun insert(exercise: ExerciseEntity) {
        dao.insert(exercise)
    }

    fun getAll(): Flow<List<ExerciseEntity>> {
        return dao.getAll()
    }

    suspend fun delete(exercise: ExerciseEntity){
        dao.delete(exercise)
    }
}

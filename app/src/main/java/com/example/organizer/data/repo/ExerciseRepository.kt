package com.example.organizer.data.repo

import com.example.organizer.data.dao.ExerciseDao
import com.example.organizer.data.entity.Exercise
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val dao: ExerciseDao) {
    suspend fun insert(exercise: Exercise) {
        dao.insert(exercise)
    }

    fun getAll(): Flow<List<Exercise>> {
        return dao.getAll()
    }

    suspend fun delete(exercise: Exercise){
        dao.delete(exercise)
    }
}
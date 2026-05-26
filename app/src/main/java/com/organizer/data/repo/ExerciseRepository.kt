package com.organizer.data.repo

import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.repo.ExerciseLocalDataSource
import com.organizer.data.remote.repo.ExerciseRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val localDataSource: ExerciseLocalDataSource,
    private val remoteDataSource: ExerciseRemoteDataSource,
) {
    fun observeExercises(): Flow<List<ExerciseEntity>> {
        return localDataSource.getAll()
    }

    suspend fun refreshExercises() {
        val remoteExercises = remoteDataSource.getAll()
        val localExercises = localDataSource.getAllOnce()

        for (exercise in localExercises) {
            if (!remoteExercises.contains(exercise) && !exercise.isCustom) {
                localDataSource.delete(exercise)
            }
        }

        localDataSource.insert(remoteExercises)
    }

    suspend fun addCustomExercise(name: String, imageUrl: String) {
        localDataSource.insert(
            listOf(
                ExerciseEntity(
                    name = name,
                    imageUrl = imageUrl,
                    categoryId = null,
                    isCustom = true
                )
            )
        )
    }

    suspend fun deleteCustomExercise(id: Long) {
        val exercise = getExercise(id)
        if (exercise != null && exercise.isCustom) {
            localDataSource.delete(exercise)
        }
    }

    suspend fun getExercise(id: Long): ExerciseEntity? {
        return localDataSource.getById(id)
    }
}

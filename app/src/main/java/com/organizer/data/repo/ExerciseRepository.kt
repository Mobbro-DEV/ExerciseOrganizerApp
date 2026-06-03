package com.organizer.data.repo

import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.repo.ExerciseLocalDataSource
import com.organizer.data.remote.repo.ExerciseRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val localDataSource: ExerciseLocalDataSource,
    private val remoteDataSource: ExerciseRemoteDataSource,
    private val fileRepository: FileRepository,
) {
    fun observeExercises(): Flow<List<ExerciseEntity>> {
        return localDataSource.getAll()
    }

    fun observeExercisesByCategory(categoryId: Long): Flow<List<ExerciseEntity>> {
        return localDataSource.getExercisesByCategory(categoryId)
    }

    suspend fun refreshExercises() {
        val remoteExercises = remoteDataSource.getAll()

        localDataSource.getAllOnce()
            .filter { it !in remoteExercises.toSet() }
            .forEach { exercise ->
                localDataSource.delete(exercise)
                fileRepository.deleteImage(exercise.imageUrl)
            }

        localDataSource.insert(remoteExercises)
        fileRepository.downloadAndSaveImages(remoteExercises.map { it.imageUrl })
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
        val exercise = observeExercise(id).firstOrNull()
        if (exercise?.isCustom == true) {
            localDataSource.delete(exercise)
        }
    }

    fun observeExercise(id: Long): Flow<ExerciseEntity?> {
        return localDataSource.getById(id)
    }
}

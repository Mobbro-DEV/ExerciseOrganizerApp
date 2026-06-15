package com.organizer.data.repo

import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.repo.ExerciseLocalDataSource
import com.organizer.data.remote.repo.ExerciseRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import kotlin.time.Clock

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

    fun observeExercisesByIds(exerciseIds: List<Long>): Flow<List<ExerciseEntity>> {
        return localDataSource.getExercisesByIds(exerciseIds)
    }

    fun observeCustomExercises(): Flow<List<ExerciseEntity>> {
        return localDataSource.getCustomExercises()
    }

    suspend fun refreshExercises() {
        val remoteExercises = remoteDataSource.getAll()

        localDataSource.getAllOnce()
            .filter { it !in remoteExercises.toSet() && !it.isCustom }
            .forEach { exercise ->
                localDataSource.delete(exercise)
                fileRepository.deleteImage(exercise.imageUrl)
            }

        localDataSource.insertAll(remoteExercises)
        fileRepository.downloadAndSaveImages(remoteExercises.map { it.imageUrl })
    }

    suspend fun addCustomExercise(name: String, imageName: String) {
        localDataSource.insert(
            ExerciseEntity(
                name = name,
                imageUrl = imageName,
                categoryId = null,
                isCustom = true
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

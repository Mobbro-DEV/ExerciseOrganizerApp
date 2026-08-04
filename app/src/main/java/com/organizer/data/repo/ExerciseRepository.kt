package com.organizer.data.repo

import com.organizer.data.local.dao.ExerciseDao
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.remote.repo.ExerciseRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val remoteDataSource: ExerciseRemoteDataSource,
    private val fileRepository: FileRepository,
) {
    suspend fun addCustomExercise(name: String, instructions: List<String>, imageName: String) {
        val instructionText = instructions.mapIndexed { index, step ->
                "${index + 1}. $step"
            }
            .joinToString(" ")

        exerciseDao.insert(
            ExerciseEntity(
                name = name,
                instruction = instructionText,
                imageUrl = imageName,
                isCustom = true
            )
        )
    }

    fun observeAllExercises(): Flow<List<ExerciseEntity>> {
        return exerciseDao.getAll()
    }

    fun observeCustomExercises(): Flow<List<ExerciseEntity>> {
        return exerciseDao.getCustomExercises()
    }

    fun observeExercisesByCategory(categoryId: Long): Flow<List<ExerciseEntity>> {
        return exerciseDao.observeExercisesByCategory(categoryId)
    }

    fun observeExercisesByWorkout(workoutId: Long) =
        exerciseDao.observeExercisesByWorkout(workoutId)

    fun observeExercise(id: Long): Flow<ExerciseEntity?> {
        return exerciseDao.getById(id)
    }

    suspend fun refreshExercises() {
        val remoteExercises = remoteDataSource.getAll()
        val remoteIds = remoteExercises.map { it.exerciseId }

        val localExercises = exerciseDao.getAllOnce()
        val localIds = localExercises.map { it.exerciseId }

        val toInsert = remoteExercises.filter { it.exerciseId !in localIds }

        val toUpdate = remoteExercises.filter { remote ->
            localExercises.any { local ->
                local.exerciseId == remote.exerciseId && local != remote
            }
        }

        val changedImages = (toInsert + toUpdate).map { it.imageUrl }
        fileRepository.downloadAndSaveImages(changedImages)

        val toDelete = localExercises.filter { local ->
            local.exerciseId !in remoteIds && !local.isCustom
        }

        toDelete.forEach { exercise ->
            exerciseDao.delete(exercise)
            fileRepository.deleteImage(exercise.imageUrl)
        }

        exerciseDao.insertAll(toInsert)
        exerciseDao.updateAll(toUpdate)
    }

    suspend fun deleteCustomExercise(id: Long) {
        val exercise = exerciseDao.getByIdOnce(id)
        if (exercise?.isCustom == true) {
            exerciseDao.delete(exercise)
        }
    }
}

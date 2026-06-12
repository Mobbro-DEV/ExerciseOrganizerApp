package com.organizer.data.repo

import com.organizer.data.local.dao.WorkoutExerciseDao
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.local.db.entities.WorkoutExerciseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutExerciseRepository @Inject constructor(
    private val dao: WorkoutExerciseDao
) {
    suspend fun addExerciseToWorkout(workoutId: Long, exerciseId: Long) {
        dao.insert(WorkoutExerciseEntity(workoutId, exerciseId))
    }

    fun observeWorkoutExercises(): Flow<List<WorkoutExerciseEntity>> {
        return dao.getAll()
    }

    fun observeExerciseIdsByWorkout(workoutId: Long): Flow<List<Long>> {
        return dao.getExerciseIdsByWorkout(workoutId)
    }

    suspend fun deleteExerciseFromWorkout(workoutId: Long, exerciseId: Long) {
        dao.delete(WorkoutExerciseEntity(workoutId, exerciseId))
    }
}

package com.organizer.data.repo

import com.organizer.data.local.dao.WorkoutDao
import com.organizer.data.local.db.entities.WorkoutEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val dao: WorkoutDao
) {
    suspend fun createWorkout(name: String) {
        dao.insert(WorkoutEntity(name = name))
    }

    fun observeWorkouts(): Flow<List<WorkoutEntity>> {
        return dao.getAll()
    }

    suspend fun deleteWorkout(id: Long) {
        dao.delete(id)
    }
}

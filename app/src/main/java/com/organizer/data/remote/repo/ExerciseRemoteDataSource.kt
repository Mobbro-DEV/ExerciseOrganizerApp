package com.organizer.data.remote.repo

import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.mapper.asEntity
import com.organizer.data.remote.ApiService
import jakarta.inject.Inject

class ExerciseRemoteDataSource @Inject constructor(
    private val api: ApiService,
) {
    suspend fun getAll(): List<ExerciseEntity> {
        return api.getExercises().map { it.asEntity() }
    }
}

package com.organizer.data.remote

import com.organizer.data.remote.model.Category
import com.organizer.entity.Exercise
import retrofit2.http.GET

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("exercises")
    suspend fun getExercises(): List<Exercise>
}

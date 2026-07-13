package com.organizer.data.remote

import com.organizer.data.remote.model.Category
import com.organizer.entity.Exercise
import retrofit2.http.GET

interface ApiService {

    @GET("sports")
    suspend fun getSports(): List<Category>

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("exercises")
    suspend fun getExercises(): List<Exercise>
}

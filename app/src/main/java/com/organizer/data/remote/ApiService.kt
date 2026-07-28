package com.organizer.data.remote

import com.organizer.data.remote.model.Category
import com.organizer.data.remote.model.Exercise
import com.organizer.data.remote.model.ExerciseCategory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {

    @GET("sports")
    suspend fun getSports(): List<Category>

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("exercises")
    suspend fun getExercises(): List<Exercise>

    @GET("exercise-category")
    suspend fun getExerciseCategory(): List<ExerciseCategory>

    @Streaming
    @GET
    suspend fun downloadFile(
        @Url url: String
    ): Response<ResponseBody>
}

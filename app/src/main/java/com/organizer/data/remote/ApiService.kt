package com.organizer.data.remote

import com.organizer.data.remote.model.Category
import retrofit2.http.GET

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<Category>
}

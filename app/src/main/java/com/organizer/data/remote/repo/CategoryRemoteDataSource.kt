package com.organizer.data.remote.repo

import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.mapper.asEntity
import com.organizer.data.remote.ApiService

class CategoryRemoteDataSource(
    private val api: ApiService,
) {
    suspend fun getAll(): List<CategoryEntity> {
        return api.getCategories().map { it.asEntity() }
    }
}

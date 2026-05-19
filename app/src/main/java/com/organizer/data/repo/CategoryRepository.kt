package com.organizer.data.repo

import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.mapper.asEntity
import com.organizer.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

class CategoryRepository(
    private val dao: CategoryDao,
    private val api: ApiService,
) {
    private suspend fun insert(categories: List<CategoryEntity>) {
        dao.insert(categories)
    }

    fun getAll(): Flow<List<CategoryEntity>> {
        return dao.getAll()
    }

    suspend fun getById(id: Long): CategoryEntity? {
        return dao.getById(id)
    }

    private suspend fun delete(category: CategoryEntity) {
        dao.delete(category)
    }

    suspend fun syncCategories() {
        val remote = api.getCategories()
        val entities = remote.map { it.asEntity() }
        dao.insert(entities)
    }
}

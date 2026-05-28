package com.organizer.data.local.repo

import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.db.entities.CategoryEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class CategoryLocalDataSource @Inject constructor(
    private val dao: CategoryDao
) {
    suspend fun insert(categories: List<CategoryEntity>) {
        dao.insert(categories)
    }

    fun getAll(): Flow<List<CategoryEntity>> {
        return dao.getAll()
    }

    suspend fun getAllOnce(): List<CategoryEntity> {
        return dao.getAllOnce()
    }

    fun getSports(): Flow<List<CategoryEntity>> {
        return dao.getSports()
    }

    fun getSubcategories(categoryId: Long): Flow<List<CategoryEntity>> {
        return dao.getSubcategories(categoryId)
    }

    fun getById(id: Long): Flow<CategoryEntity?> {
        return dao.getById(id)
    }

    suspend fun delete(category: CategoryEntity) {
        dao.delete(category)
    }
}

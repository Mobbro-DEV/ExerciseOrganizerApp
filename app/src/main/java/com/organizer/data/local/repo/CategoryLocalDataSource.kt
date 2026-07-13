package com.organizer.data.local.repo

import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.db.entities.CategoryEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class CategoryLocalDataSource @Inject constructor(
    private val dao: CategoryDao
) {
    suspend fun insertAll(categories: List<CategoryEntity>) {
        dao.insertAll(categories)
    }

    suspend fun updateAll(categories: List<CategoryEntity>) {
        dao.updateAll(categories)
    }

    fun getAll(): Flow<List<CategoryEntity>> {
        return dao.getAll()
    }

    suspend fun getCategoriesOnce(): List<CategoryEntity> {
        return dao.getCategoriesOnce()
    }

    fun getSports(): Flow<List<CategoryEntity>> {
        return dao.getSports()
    }

    suspend fun getSportsOnce(): List<CategoryEntity> {
        return dao.getSportsOnce()
    }

    fun getSubcategories(categoryId: Long): Flow<List<CategoryEntity>> {
        return dao.getSubcategories(categoryId)
    }

    fun getById(id: Long): Flow<CategoryEntity?> {
        return dao.getById(id)
    }

    suspend fun getByIdOnce(id: Long): CategoryEntity? {
        return dao.getByIdOnce(id)
    }

    suspend fun delete(category: CategoryEntity) {
        dao.delete(category)
    }
}

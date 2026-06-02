package com.organizer.data.repo

import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.repo.CategoryLocalDataSource
import com.organizer.data.local.repo.IconStorage
import com.organizer.data.remote.repo.CategoryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource,
    private val iconStorage: IconStorage,
) {
    fun observeCategories(): Flow<List<CategoryEntity>> {
        return localDataSource.getAll()
    }

    fun observeSports(): Flow<List<CategoryEntity>> {
        return localDataSource.getSports()
    }

    fun observeSubcategories(categoryId: Long): Flow<List<CategoryEntity>> {
        return localDataSource.getSubcategories(categoryId)
    }

    suspend fun refreshCategories() {
        val remoteCategories = remoteDataSource.getAll()
        val localCategories = localDataSource.getAllOnce()

        for (category in localCategories) {
            if (!remoteCategories.contains(category)) {
                localDataSource.delete(category)
                category.iconUrl?.let { iconStorage.deleteIcon(it) }
            }
        }

        iconStorage.saveIcons(
            remoteCategories.mapNotNull {
                it.iconUrl
            }
        )
        localDataSource.insert(remoteCategories)
    }

    fun observeCategoryById(id: Long): Flow<CategoryEntity?> {
        return localDataSource.getById(id)
    }

    suspend fun observeCategoryByIdOnce(id: Long): CategoryEntity? {
        return localDataSource.getByIdOnce(id)
    }
}

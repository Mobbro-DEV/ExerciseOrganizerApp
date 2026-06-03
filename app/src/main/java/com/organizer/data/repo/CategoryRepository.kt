package com.organizer.data.repo

import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.repo.CategoryLocalDataSource
import com.organizer.data.remote.repo.CategoryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource,
    private val fileRepository: FileRepository,
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

        localDataSource.getAllOnce()
            .filter { it !in remoteCategories.toSet() }
            .forEach { category ->
                localDataSource.delete(category)
                fileRepository.deleteIcon(category.iconUrl)
            }

        localDataSource.insert(remoteCategories)
        fileRepository.downloadAndSaveIcons(remoteCategories.map { it.iconUrl })
    }

    fun observeCategoryById(id: Long): Flow<CategoryEntity?> {
        return localDataSource.getById(id)
    }

    suspend fun observeCategoryByIdOnce(id: Long): CategoryEntity? {
        return localDataSource.getByIdOnce(id)
    }
}

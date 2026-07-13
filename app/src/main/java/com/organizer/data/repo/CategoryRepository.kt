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
        val remoteIds = remoteCategories.map { it.categoryId }

        val localCategories = localDataSource.getAllOnce()
        val localIds = localCategories.map { it.categoryId }

        val toInsert = remoteCategories.filter { it.categoryId !in localIds }

        val toUpdate = remoteCategories.filter { remote ->
            localCategories.any { local ->
                local.categoryId == remote.categoryId && local != remote
            }
        }

        val toDelete = localCategories.filter { local ->
            local.categoryId !in remoteIds
        }

        toDelete.forEach { category ->
            localDataSource.delete(category)
            fileRepository.deleteIcon(category.iconUrl)
        }

        localDataSource.insertAll(toInsert)
        localDataSource.updateAll(toUpdate)

        val changedIcons = (toInsert + toUpdate).map { it.iconUrl }
        fileRepository.downloadAndSaveIcons(changedIcons)
    }

    fun observeCategoryById(id: Long): Flow<CategoryEntity?> {
        return localDataSource.getById(id)
    }

    suspend fun observeCategoryByIdOnce(id: Long): CategoryEntity? {
        return localDataSource.getByIdOnce(id)
    }
}

package com.organizer.data.repo

import com.organizer.data.local.dao.CategoryDao
import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.remote.repo.CategoryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val remoteDataSource: CategoryRemoteDataSource,
    private val fileRepository: FileRepository,
) {
    fun observeSports(): Flow<List<CategoryEntity>> {
        return categoryDao.getSports()
    }

    fun observeSubcategories(categoryId: Long): Flow<List<CategoryEntity>> {
        return categoryDao.getSubcategories(categoryId)
    }

    suspend fun refreshSports() {
        val remoteSports = remoteDataSource.getSports()
        val localSports = categoryDao.getSportsOnce()
        syncCategories(remoteSports, localSports)
    }

    suspend fun refreshCategories() {
        val remoteCategories = remoteDataSource.getCategories()
        val localCategories = categoryDao.getCategoriesOnce()
        syncCategories(remoteCategories, localCategories)
    }

    private suspend fun syncCategories(remoteCategories: List<CategoryEntity>, localCategories: List<CategoryEntity>) {
        val remoteIds = remoteCategories.map { it.categoryId }
        val localIds = localCategories.map { it.categoryId }

        val toInsert = remoteCategories.filter { it.categoryId !in localIds }

        val toUpdate = remoteCategories.filter { remote ->
            localCategories.any { local ->
                local.categoryId == remote.categoryId && local != remote
            }
        }

        val changedIcons = (toInsert + toUpdate).mapNotNull { it.iconUrl }
        fileRepository.downloadAndSaveIcons(changedIcons)

        val toDelete = localCategories.filter { local ->
            local.categoryId !in remoteIds
        }

        toDelete.forEach { category ->
            categoryDao.delete(category)
            fileRepository.deleteIcon(category.iconUrl)
        }

        categoryDao.insertAll(toInsert)
        categoryDao.updateAll(toUpdate)
    }

    suspend fun observeCategoryByIdOnce(id: Long): CategoryEntity? {
        return categoryDao.getByIdOnce(id)
    }

    fun getCategoryPath(categoryId: Long): Flow<List<CategoryEntity>> = flow {
        val path = mutableListOf<CategoryEntity>()

        var current = observeCategoryByIdOnce(categoryId)

        while (current != null) {
            path.add(current)
            current = current.parentCategoryId?.let {
                observeCategoryByIdOnce(it)
            }
        }

        emit(path.reversed())
    }
}

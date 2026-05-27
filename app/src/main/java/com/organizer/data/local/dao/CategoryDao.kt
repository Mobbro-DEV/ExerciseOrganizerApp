package com.organizer.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.organizer.data.local.db.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<CategoryEntity>)

    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category")
    suspend fun getAllOnce(): List<CategoryEntity>

    @Query("SELECT * FROM category WHERE parentCategoryId IS NULL")
    fun getSports(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category WHERE categoryId = :id")
    suspend fun getById(id: Long): CategoryEntity?

    @Delete
    suspend fun delete(category: CategoryEntity)
}

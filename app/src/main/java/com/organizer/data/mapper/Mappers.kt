package com.organizer.data.mapper

import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.remote.model.Category
import com.organizer.entity.Exercise

fun Category.asEntity() = CategoryEntity(
    categoryId = categoryId,
    name = name,
    parentCategoryId = parentCategory?.categoryId
)

fun Exercise.asEntity() = ExerciseEntity(
    exerciseId = exerciseId,
    name = name,
    imageUrl = imageUrl,
    categoryId = category?.categoryId,
    isCustom = false,
)

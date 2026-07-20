package com.organizer.data.mapper

import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.local.db.entities.ExerciseEntity
import com.organizer.data.remote.model.Category
import com.organizer.data.remote.model.Exercise

fun Category.asEntity() = CategoryEntity(
    categoryId = categoryId,
    name = name,
    iconUrl = iconUrl,
    parentCategoryId = parentCategoryId
)

fun Exercise.asEntity() = ExerciseEntity(
    exerciseId = exerciseId,
    name = name,
    instruction = instruction,
    imageUrl = imageUrl,
    categoryId = categoryId,
    isCustom = false,
)

package com.organizer.data.mapper

import com.organizer.data.local.db.entities.CategoryEntity
import com.organizer.data.remote.model.Category

fun Category.asEntity() = CategoryEntity(
    categoryId = categoryId,
    name = name,
    parentCategoryId = parentCategory?.categoryId
)

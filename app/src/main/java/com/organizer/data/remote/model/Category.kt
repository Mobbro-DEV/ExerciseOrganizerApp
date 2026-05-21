package com.organizer.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Category (
    val categoryId: Long,
    val name: String,
    val parentCategory: Category?,
)

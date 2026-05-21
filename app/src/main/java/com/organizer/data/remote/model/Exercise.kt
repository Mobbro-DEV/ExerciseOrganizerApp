package com.organizer.entity

import com.organizer.data.remote.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class Exercise (
    val exerciseId: Long,
    val name: String,
    val imageUrl: String,
    val category: Category?,
)

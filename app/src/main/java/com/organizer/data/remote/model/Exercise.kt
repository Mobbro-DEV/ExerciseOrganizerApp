package com.organizer.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Exercise (
    val exerciseId: Long,
    val name: String,
    val instruction: String,
    val imageUrl: String,
    val category: Category?,
)

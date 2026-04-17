package com.example.organizer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class ExerciseEntity(
    @PrimaryKey
    var eId: String = UUID.randomUUID().toString(),
    var name: String,
    var imageUrl: String,
    var category: String,
    val updatedAt: Long = System.currentTimeMillis()
)

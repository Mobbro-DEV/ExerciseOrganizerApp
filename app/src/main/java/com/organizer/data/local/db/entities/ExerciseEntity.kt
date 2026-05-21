package com.organizer.data.local.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    "exercise",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class ExerciseEntity(
    @PrimaryKey
    val exerciseId: Long,
    val name: String,
    val imageUrl: String,
    val categoryId: Long?,
)

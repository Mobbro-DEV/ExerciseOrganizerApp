package com.organizer.data.local.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "exercise",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index("name", unique = true),
        Index("categoryId"),
        Index("isCustom"),
    ]
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long = 0L,
    val name: String,
    val instruction: String?,
    val imageUrl: String,
    val categoryId: Long?,
    val isCustom: Boolean,
)
